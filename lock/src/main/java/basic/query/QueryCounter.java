package basic.query;

public class QueryCounter {

    private Count count;
    private boolean countable;

    public QueryCounter() {
        count = new Count(0);
        countable = false;
    }

    public void startCount() {
        count = new Count(0);
        countable = true;
    }

    public void countOne(){
        if(!isCountable()){
            throw new RuntimeException("[Error] 아직 카운트를 시작하지 않았습니다.");
        }
        count = count.countOne();
    }

    public Count getCount() {
        return count;
    }

    public void endCount(){
        countable = false;
    }

    public boolean isCountable() {
        return countable;
    }
}