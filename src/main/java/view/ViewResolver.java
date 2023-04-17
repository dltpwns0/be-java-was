package view;

import java.util.Collection;

public class ViewResolver {

    private Collection<View> views;

    public ViewResolver(Collection<View> views) {
        this.views= views;
    }

    public View resolve(String viewName) {
        for (View view : views) {
            Class<? extends View> viewClass = view.getClass();
            String name = view.getName();

            if (viewName.startsWith(name)) {
                return view;
            }
        }
        // TODO  : 밑의 null은 나중에 처리하겠음
        return null;
    }

}
