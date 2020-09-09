package inc.app.mes.recycler;

public class ItemRecyclerView {

    private String title;
    private String state;

    public ItemRecyclerView(String title, String state) {
        this.title = title;
        this.state = state;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return state;
    }

    public void setDate(String date) {
        this.state = date;
    }
}