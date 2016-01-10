package hr.foi.air.t18.webservice;

/**
 * Created by Danijel on 10.1.2016..
 */
public class Pair<T1, T2>
{
    private T1 value1;
    private T2 value2;

    public Pair(T1 value1, T2 value2)
    {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 getFirstValue()
    {
        return value1;
    }

    public T2 getSecondValue()
    {
        return value2;
    }
}
