public class Courier {
    private  String login;
    private  String password;
    private  String firstName;


    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getFullJson() {
        String json = String.format("{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}", this.login, this.password, this.firstName);
        return json;
    }

    public String getNoFirstNameJson() {
        String json = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", this.login, this.password);
        return json;
    }

    public String getNoLoginJson() {
        String json = String.format("{\"password\": \"%s\", \"firstName\": \"%s\"}", this.password, this.firstName);
        return json;
    }

    public String getNoPasswordJson() {
        String json = String.format("{\"login\": \"%s\", \"firstName\": \"%s\"}", this.login, this.firstName);
        return json;
    }

    public String getNoPasswordNoLoginJson() {
        String json = String.format("{\"firstName\": \"%s\"}", this.firstName);
        return json;
    }

    public String getUncorrectLoginJson() {
        String json = String.format("{\"login\": \"%s11\", \"password\": \"%s\"}", this.login, this.password);
        return json;
    }

    public String getUncorrectPasswordJson() {
        String json = String.format("{\"login\": \"%s11\", \"password\": \"%s11\"}", this.login, this.password);
        return json;
    }

    public String getEmptyPasswordJson() {
        String json = String.format("{\"login\": \"%s\", \"password\": \"\"}", this.login);
        return json;
    }

}

