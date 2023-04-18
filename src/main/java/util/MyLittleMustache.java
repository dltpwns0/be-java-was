package util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyLittleMustache {

    Interpreter interpreter;
    public MyLittleMustache(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    private final String OBJECT_AND_METHOD = "\\{\\{\\s*([^#^}^/\\s]+)\\s*\\}\\}";
    private final String SECTION = "\\{\\{\\s*\\#(.*?)\\s*\\}\\}((?s).*?)\\{\\{\\s*\\/(\\1)\\s*\\}\\}";


    private final String NULL_STRING = "";

    private final String TRUE = "true";
    private final String FALSE = "false";

    private Pattern pattern;
    private final int GROUP_ZERO = 0;
    private final int GROUP_ONE = 1;
    private final int GROUP_TWO = 2;
    private final int GROUP_THREE = 3;


    public String render(String html) {

        html = replaceSection(html);

        html = replaceObjectAndMethod(html);
        return html;
    }

    private String replaceCondition(String html, Map<String, Collection<String>> map, String condition) {
        for (String key : map.keySet()) {
            String interpret = interpreter.interpretValue(key);
            ArrayList<String> context = (ArrayList<String>) map.get(key);
            if (interpret.equals(condition)) {
                html = html.replace(context.get(GROUP_ZERO), context.get(GROUP_ONE));
                continue;
            }
            html = html.replace(context.get(GROUP_ZERO), NULL_STRING);
        }
        return html;
    }

    private String replaceSection(String template) {
        StringBuilder output = new StringBuilder();
        pattern = Pattern.compile(SECTION);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String objectName = matcher.group(GROUP_ONE);
            Object object = interpreter.interpretObject(objectName);
            if (object == null) {
                matcher.appendReplacement(output, "");
            }

            if (object instanceof Boolean) {
                if (object.equals(true)) {
                    matcher.appendReplacement(output, matcher.group(GROUP_TWO));
                }
                else {
                    matcher.appendReplacement(output, "");
                }
            }
            if (object instanceof Collection<?>) {
                Collection<Object> objects = (Collection<Object>) object;
                String context = matcher.group(GROUP_TWO);
                StringBuilder tempOutput = new StringBuilder();
                pattern = Pattern.compile(OBJECT_AND_METHOD);
                for (Object o : objects) {
                    Matcher matcherOfIterator = pattern.matcher(context);
                    StringBuilder matchOutput = new StringBuilder();
                    while (matcherOfIterator.find()) {
                        String methodName = matcherOfIterator.group(GROUP_ONE);
                        String result = interpreter.interpretObjectAndMethodName(o, methodName);
                        matcherOfIterator.appendReplacement(matchOutput, result);
                    }
                    matcherOfIterator.appendTail(matchOutput);
                    tempOutput.append(matchOutput);
                }

                matcher.appendReplacement(output, tempOutput.toString());

            }
        }
        matcher.appendTail(output);
        return output.toString();
    }

    private String replaceObjectAndMethod(String template) {
        StringBuilder output = new StringBuilder();
        pattern = Pattern.compile(OBJECT_AND_METHOD);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String objectAndMethodName = matcher.group(GROUP_ONE);
            String result = interpreter.interpretObjectAndMethodName(objectAndMethodName);

            if (result != null) {
                matcher.appendReplacement(output, result);
            }
        }
        matcher.appendTail(output);
        return output.toString();
    }
}
