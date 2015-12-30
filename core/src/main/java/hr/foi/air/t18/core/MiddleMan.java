package hr.foi.air.t18.core;

/**
 * Class used to temporarily store required data
 * when switching between activities.
 *
 * Created by Danijel on 3.12.2015..
 */
public class MiddleMan
{
    private static Object _object;

    /**
     * Stores the object needed to transfer.
     * @param object Object needed to transfer
     */
    public static void setObject(Object object)
    {
        _object = object;
    }

    /**
     * Gets the stored object.
     * @return Stored object
     */
    public static Object getObject()
    {
        return _object;
    }
}
