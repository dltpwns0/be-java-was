package session;

public class Cookie {

    private String name;
    private String value;

    private String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("=").append(value);
        if (path != null) {
            sb.append("; ").append("Path").append("=").append(path);
        }
        return sb.toString();
    }
}
