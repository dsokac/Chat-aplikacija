package hr.foi.air.t18.core.State;

/**
 * Created by JurmanLap on 11.1.2016..
 */
public class Context {
    private State state;

    public Context(){
        state = null;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
