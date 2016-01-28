package hr.foi.air.t18.webservice;

/**
 * Class that puts two objects together.
 */
public class Pair<T1, T2>
{
    private T1 value1;
    private T2 value2;

    /**
     * Pair constructor
     * @param value1
     * @param value2
     */
    public Pair(T1 value1, T2 value2)
    {
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * Returns first value of the pair.
     * @return first value of the pair.
     */
    public T1 getFirstValue()
    {
        return value1;
    }

    /**
     * Returns second value of the pair.
     * @return second value of the pair.
     */
    public T2 getSecondValue()
    {
        return value2;
    }
}
