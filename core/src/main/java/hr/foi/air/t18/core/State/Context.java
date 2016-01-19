package hr.foi.air.t18.core.State;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Context {
    private IState state;

    public Context(){
        state = null;
    }

    public void setState(IState state){
        this.state = state;
    }

    public IState getState(){
        return state;
    }
}
