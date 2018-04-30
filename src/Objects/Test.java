package Objects;

public class Test {
    private String categories;
    private String name;
    private String passFail;
    private String time;

    public Test(String c, String n, String pf, String t){
        categories = c;
        name = n;
        passFail = pf;
        time = t;
    }

    public String getCategories(){
        return categories;
    }

    public String getName() {
        return name;
    }

    public String getPassFail() {
        return passFail;
    }

    public String getTime() {
        return time;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassFail(String passFail) {
        this.passFail = passFail;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
