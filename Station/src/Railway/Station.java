package Railway;

/**
 * Created by IntelliJ IDEA.
 * User: Lasitha
 * Date: 1/22/12
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Station {
    private String stationName;
    private int stationCode;
    private int lineCount;
    private Line[] line;
    private int platformCount;
    private int subLineCount;
    private Station[] nearStation;
    public Station(String aName,int aCode){
        stationName=aName;
        stationCode=aCode;
    }
    public void setLineCount(int num){
        lineCount=num;        
    }
    public void setNearStation(Station station1,Station station2){
        nearStation=new Station[2];
        nearStation[0]=station1;
        nearStation[1]=station2;
    }
    public String giveStationName(){
        return stationName;
    }
    public int giveStationCode(){
        return stationCode;
    }
    public void setLine(int num,Station station,char code,int gateCount){
        line[num]=new Line(code,station,this,gateCount);
    }
}

class Line{
    private char lineCode;
    private Station[] terminal;
    private boolean occupied;
    private Gate[] gates;
    private int gateCount=0,noOfGates;
    public Line(char code,Station station1,Station station2,int numOfGates){
        lineCode=code;
        terminal=new Station[2];
        terminal[0]=station1;
        terminal[1]=station2;
        noOfGates=numOfGates;
        gates=new Gate[noOfGates];
        occupied=false;
    }
    public char giveLineCode(){
        return lineCode;
    }
    public boolean isOccupied(){
        return occupied;
    }
    public void addGate(Gate aGate){
        if(gateCount<noOfGates){
            gates[gateCount++]=aGate;
        }else{
            throw new IndexOutOfBoundsException();
        }
    }
    public void allocate(){
        if(!occupied){
            occupied=true;
            for(gateCount=0;gateCount<noOfGates;gateCount++){
                gates[gateCount].closeGate();
            }
            //notify observers
        }else{
            //wait and try again
            try {
                Thread.sleep(5000);
                allocate();
            } catch (InterruptedException e) {}
        }
    }
    public void deallocate(){
        if(occupied){
            occupied=false;
            for(gateCount=0;gateCount<noOfGates;gateCount++){
                gates[gateCount].openGate();
            }
        }
    }
}

class Gate{
    private char code;
    private int distanceFromStation;
    private boolean closed;
    
    public Gate(char aCode,int dist){
        code=aCode;
        distanceFromStation=dist;
        closed=false;
    }
    public boolean isClosed(){
        return closed;
    }
    public void closeGate(){
        if(!closed){
            closed=true;
            //have to notify observers
        }
    }
    public void openGate(){
        if(closed){
            closed=false;
            //have to notify observers
        }
    }
}