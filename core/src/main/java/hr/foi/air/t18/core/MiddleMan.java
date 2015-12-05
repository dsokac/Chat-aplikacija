package hr.foi.air.t18.core;

/**
 * Created by Danijel on 3.12.2015..
 */
public class MiddleMan
{
    private static Object _object;

    public static void setObject(Object object)
    {
        _object = object;
    }

    public static Object getObject()
    {
        return _object;
    }
}
