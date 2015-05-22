package stupiderboxgame;

import java.util.ArrayList;
import java.lang.Math;

public class PerfectBox{
    public static final double UNIVERSAL_EPSILON_VAL = 0.00000000001;
    
    float width;
    float height;
    Coordinates centerPoint;
    Coordinates SW;
    Coordinates NW;
    Coordinates SE;
    Coordinates NE;
    
    /*All object types that the box has handling for are contained in this list? 
    ArrayList<PerfectBox> collisionReferenceList = new ArrayList<PerfectBox>();
    */
    
    public PerfectBox(float w, float h, Coordinates center){
        width = w;
        height = h;
        centerPoint = center;
        
        SW = new Coordinates(centerPoint.getX()-0.5f*width, centerPoint.getY()-0.5f*height);
        NW = new Coordinates(centerPoint.getX()-0.5f*width, centerPoint.getY()+0.5f*height);
        SE = new Coordinates(centerPoint.getX()+0.5f*width, centerPoint.getY()-0.5f*height);
        NE = new Coordinates(centerPoint.getX()+0.5f*width, centerPoint.getY()+0.5f*height);
    }
    
    public PerfectBox(){
        
    }
    
    public boolean hasCollidedWith(PerfectBox b){
        if(this.anyPointsAreInPerfectBox(b.getCorners()) || b.anyPointsAreInPerfectBox(this.getCorners())){
            return true;
        }
        
        Coordinates[] cornerPairsThis = {SW, NW, NW, NE, NE, SE, SE, SW};
        Coordinates[] cornerPairsB = {b.getSW(), b.getNW(), b.getNW(), b.getNE(), b.getNE(), b.getSE(), b.getSE(), b.getSW()};
        for(int i = 0; i < 7; i+=2){
            if(PerfectBox.perfectLineIntersectsPerfectLine(cornerPairsThis[i], cornerPairsThis[i+1], cornerPairsB[i], cornerPairsB[i+1])){
                return true;
            }
        }
        return false;
    }
    
    
    public void handleCollisionWithBox(PerfectBox pb) {
        //Depends on the type of box collided with
        //System.out.println("Inside handleColl in PerfBox");
    }
    
    public void handleCollisionWithBox(PerfectBox pb, Screen s, EventManager em){
        
    }
    
    public static boolean perfectLineIntersectsPerfectLine(Coordinates startOne, Coordinates endOne, Coordinates startTwo, Coordinates endTwo){
        boolean oneXWise = Math.abs(startOne.getX()-endOne.getX()) < UNIVERSAL_EPSILON_VAL ? true : false;
        boolean twoXWise = Math.abs(startTwo.getX()-endTwo.getX()) < UNIVERSAL_EPSILON_VAL ? true : false;
        
        //The switch keyword is for fags anyway #ifStatementSpam4lyfe
        if(oneXWise && twoXWise){
            if(Math.abs(startOne.getX()-startTwo.getX()) < UNIVERSAL_EPSILON_VAL){
                return true;
            }
            else{
                return false;
            }
        }
        else if(!oneXWise && !twoXWise){
            if(Math.abs(startOne.getY()-startTwo.getY()) < UNIVERSAL_EPSILON_VAL){
                return true;
            }
            else{
                return false;
            }
        }
        else if(!oneXWise && twoXWise){
            //The way I do if/else strings, the redundant, additional conditional for oneLeastX actually means the same performance
            float oneGreatestX = startOne.getX() >= endOne.getX() ? startOne.getX() : endOne.getX();
            float oneLeastX = startOne.getX() >= endOne.getX() ? endOne.getX() : startOne.getX();
            if(oneLeastX <= startTwo.getX() && startTwo.getX() <= oneGreatestX){
                return true;
            }
            else{
                return false;
            }
        }
        else if(oneXWise && !twoXWise){
            float twoGreatestX = startOne.getX() >= endOne.getX() ? startOne.getX() : endOne.getX();
            float twoLeastX = startOne.getX() >= endOne.getX() ? endOne.getX() : startOne.getX();
            if(twoLeastX <= startTwo.getX() && startTwo.getX() <= twoGreatestX){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public static Coordinates perfectLineIntersectsPerfectLineAt(Coordinates perfLineStartOne, Coordinates perfLineEndOne, Coordinates perfLineStartTwo, Coordinates perfLineEndTwo) {

        float regularDimLineOne;
        float regularDimLineTwo;
        float leastDimOfLineOne = 0;
        float greatestDimOfLineOne = 0;
        float leastDimOfLineTwo = 0;
        float greatestDimOfLineTwo = 0;

        boolean oneXWise = false;
        boolean twoXWise = false;

        //Find start line's dims
        if (Math.abs(perfLineStartOne.getX() - perfLineEndOne.getX()) < UNIVERSAL_EPSILON_VAL) {
            oneXWise = true;
            regularDimLineOne = perfLineStartOne.getX();
            //Find the least and greatest of the varying dimension of the line
            if (perfLineStartOne.getY() >= perfLineEndOne.getY()) {
                leastDimOfLineOne = perfLineEndOne.getY();
                greatestDimOfLineOne = perfLineStartOne.getY();
            } else if (perfLineStartOne.getY() <= perfLineEndOne.getY()) {
                leastDimOfLineOne = perfLineStartOne.getY();
                greatestDimOfLineOne = perfLineEndOne.getY();
            } else {
                //Equal
            }
        } else if (Math.abs(perfLineStartOne.getY() - perfLineEndOne.getY()) < UNIVERSAL_EPSILON_VAL) {
            oneXWise = false;
            regularDimLineOne = perfLineStartOne.getY();
            //Find the greatest and least of the varying dimensions of the line
            if (perfLineStartOne.getX() >= perfLineEndOne.getX()) {
                leastDimOfLineOne = perfLineEndOne.getX();
                greatestDimOfLineOne = perfLineStartOne.getX();
            } else if (perfLineStartOne.getX() <= perfLineEndOne.getX()) {
                leastDimOfLineOne = perfLineStartOne.getX();
                greatestDimOfLineOne = perfLineEndOne.getX();
            } else {
                //Equal?
            }
        } else {
            //Line one is not a perfect line, waddafuck
            //perfLineEndOne.printCoordinates();
            //perfLineStartOne.printCoordinates();
            //System.out.println("Line one is not perfect. Returning null.");
            return null;
        }

        //Find second line's dims
        if (Math.abs(perfLineStartTwo.getX() - perfLineEndTwo.getX()) < UNIVERSAL_EPSILON_VAL) {
            twoXWise = true;
            regularDimLineTwo = perfLineStartTwo.getX();
            //Find varying dimension least and greatest, etc.
            if (perfLineStartTwo.getY() >= perfLineEndTwo.getY()) {
                leastDimOfLineTwo = perfLineEndTwo.getY();
                greatestDimOfLineTwo = perfLineStartTwo.getY();
            } else if (perfLineStartTwo.getY() <= perfLineEndTwo.getY()) {
                leastDimOfLineTwo = perfLineStartTwo.getY();
                greatestDimOfLineTwo = perfLineEndTwo.getY();
            } else {
                //Equal?
            }
        } else if (Math.abs(perfLineStartTwo.getY() - perfLineEndTwo.getY()) < UNIVERSAL_EPSILON_VAL) {
            twoXWise = false;
            regularDimLineTwo = perfLineStartTwo.getY();
            if (perfLineStartTwo.getX() >= perfLineEndTwo.getX()) {
                leastDimOfLineTwo = perfLineEndTwo.getX();
                greatestDimOfLineTwo = perfLineStartTwo.getX();
            } else if (perfLineStartTwo.getX() <= perfLineEndTwo.getX()) {
                leastDimOfLineTwo = perfLineStartTwo.getX();
                greatestDimOfLineTwo = perfLineEndTwo.getX();
            } else {
                //Equal?
            }
        } else {
            //Line two is not perfect, waddafuck
            //System.out.println("Line two is not perfect. Returning null.");
            return null;
        }

        //System.out.println("oneXWise: " + oneXWise + ", twoXWise: " + twoXWise);
        
        //Start comparing and finding points
        if (!oneXWise && twoXWise) {
            if ((leastDimOfLineOne <= regularDimLineTwo && regularDimLineTwo <= greatestDimOfLineOne)
                    && (leastDimOfLineTwo <= regularDimLineOne && regularDimLineOne <= greatestDimOfLineTwo)) {
                return new Coordinates(perfLineStartTwo.getX(), perfLineStartOne.getY());
            } 
            else {
                return null;
            }
        } 
        else if (oneXWise && !twoXWise) {
            if ((leastDimOfLineOne <= regularDimLineTwo && regularDimLineTwo <= greatestDimOfLineOne)
                    && (leastDimOfLineTwo <= regularDimLineOne && regularDimLineOne <= greatestDimOfLineTwo)) {
                return new Coordinates(perfLineStartOne.getX(), perfLineStartTwo.getY());
            }
            else{
                return null;
            }
        }
        else if ((oneXWise && twoXWise)
                || (!oneXWise && !twoXWise)) {
            /*
             ALL OF THE BELOW CODE IS APPLIED ONLY WHEN ONEXWISE && TWOXWISE || !ONEXWISE && !TWOXWISE, KEEP CALM AND CARRY ON WITH OTHERS
             */
            //Check if regular dims are the same
            if (!(Math.abs(regularDimLineOne - regularDimLineTwo) < UNIVERSAL_EPSILON_VAL)) {
                //System.out.println("RegularDims are not the same");
                return null;
            }

            if (!((leastDimOfLineOne <= leastDimOfLineTwo && leastDimOfLineTwo <= greatestDimOfLineOne)
                    || (leastDimOfLineOne <= greatestDimOfLineTwo && greatestDimOfLineTwo <= greatestDimOfLineOne)
                    || (leastDimOfLineTwo <= leastDimOfLineOne && leastDimOfLineOne <= greatestDimOfLineTwo)
                    || (leastDimOfLineTwo <= greatestDimOfLineOne && greatestDimOfLineOne <= greatestDimOfLineTwo))) {
                return null;
            }

            //Only 2 possibilites here
            //Check if line one's points are both inside lineTwo
            if ((leastDimOfLineTwo <= leastDimOfLineOne && greatestDimOfLineOne <= greatestDimOfLineTwo)
                    && (leastDimOfLineTwo <= greatestDimOfLineOne && greatestDimOfLineOne <= greatestDimOfLineTwo)) {
                float distOne = PerfectBox.findDistanceBetween(perfLineStartOne, perfLineStartTwo);
                float distTwo = PerfectBox.findDistanceBetween(perfLineEndOne, perfLineStartTwo);

                //Find the point of perfLineOne that is closest to perfLineStartOne
                //If the start point is farther away from perfLineTwo's start
                if (distOne > distTwo) {
                    return perfLineEndOne;
                } //If the end point is farther away from perfLineTwo's start
                else if (distTwo > distOne) {
                    return perfLineStartOne;
                } //If the distance is equal, then perfLine's points are right on top of each other
                else {
                    return perfLineStartOne;
                }
            } //At least one point, if not two, are inside lineOne
            else {
                //Find which points of lineTwo are within line One's bounds
                Coordinates[] lineTwoPoints = {perfLineStartTwo, perfLineEndTwo};
                ArrayList<Coordinates> pointsBetweenLineOne = new ArrayList<Coordinates>(2);
                int pointsCount = 0;
                for (int i = 0; i < 2; i++) {
                    if (twoXWise) {
                        if (leastDimOfLineOne <= lineTwoPoints[i].getY() && lineTwoPoints[i].getY() <= greatestDimOfLineOne) {
                            pointsBetweenLineOne.add(lineTwoPoints[i]);
                            pointsCount++;
                        } 
                    }
                        else if (!twoXWise) {
                            if (leastDimOfLineOne <= lineTwoPoints[i].getX() && lineTwoPoints[i].getX() <= greatestDimOfLineOne) {
                                pointsBetweenLineOne.add(lineTwoPoints[i]);
                                pointsCount++;
                            }
                        } 
                        else {
                            return null;
                        }
                 }
                    if (pointsBetweenLineOne.size() == 1) {
                        return pointsBetweenLineOne.get(0);
                    } //Both points of lineTwo are between lineOne
                    else if (pointsBetweenLineOne.size() == 2) {
                        float distOne = PerfectBox.findDistanceBetween(perfLineStartTwo, perfLineStartOne);
                        float distTwo = PerfectBox.findDistanceBetween(perfLineEndTwo, perfLineStartOne);

                        //Find the point of perfLineTwo that is closest to perfLineStartOne
                        //If startPoit of line two is farther away than endPoint
                        if (distOne > distTwo) {
                            return perfLineEndTwo;
                        } //If the end point is farther away from perfLineOne's start
                        //If end point of lineTwo is farthere away than start point
                        else if (distTwo > distOne) {
                            return perfLineStartTwo;
                        } //If the distance is equal, then perfLine's points are right on top of each other
                        else {
                            return perfLineStartTwo;
                        }
                    } 
                    else {
                        return null;
                    }
                }
            }
        return null;
    }
    
    public boolean anyPointsAreInPerfectBox(Coordinates[] c){
        for(Coordinates coords : c){
            if(
                (this.findLeastX() <= coords.getX() && coords.getX() <= this.findGreatestX()) &&
                (this.findLeastY() <= coords.getY() && coords.getY() <= this.findGreatestY())
            ){
                return true;
            }
        }
        return false;
    }
    
    public void translateBox(float xDist, float yDist){
        SW = new Coordinates(SW.getX()+xDist, SW.getY()+yDist);
        NW = new Coordinates(NW.getX()+xDist, NW.getY()+yDist);
        SE = new Coordinates(SE.getX()+xDist, SE.getY()+yDist);
        NE = new Coordinates(NE.getX()+xDist, NE.getY()+yDist);
        centerPoint = new Coordinates(centerPoint.getX()+xDist, centerPoint.getY()+yDist);
    }
    
    public void translateBoxAtAngle(float angle, float dist){
        //Convert angle/dist combo into a stock x/y translation and then translate
        float xDist = (float) Math.cos(Math.toRadians(angle))*dist;
        float yDist = (float) Math.sin(Math.toRadians(angle))*dist;
        this.translateBox(xDist, yDist);
    }
    
    public void setWidth(float w){
        width = w;
    }
    
    public void setHeight(float h){
        height = h;
    }
    
    public void setCenterPoint(Coordinates center){
        centerPoint = center;
    }
    
    public static float findDistanceBetween(Coordinates startPoint, Coordinates endPoint) {
        //d = squareRootOf( (x2-x1)^2 + (y2-y1)^2 )
        float dist = (float) Math.sqrt(Math.pow(Math.abs(endPoint.getX() - startPoint.getX()), 2) + Math.pow(Math.abs(endPoint.getY() - startPoint.getY()), 2));
        return dist;
    }
    
    public float findGreatestX(){
        float[] xCoords = {SW.getX(), NW.getX(), SE.getX(), NE.getX()};
        float greatestX = PerfectBox.findGreatestFloatOf(xCoords);
        return greatestX;
    }
    
    public float findGreatestY(){
        float[] yCoords = {SW.getY(), NW.getY(), SE.getY(), NE.getY()};
        float greatestY = PerfectBox.findGreatestFloatOf(yCoords);
        return greatestY;
    }
    
    public float findLeastX(){
        float[] xCoords = {SW.getX(), NW.getX(), SE.getX(), NE.getX()};
        return PerfectBox.findLeastFloatOf(xCoords);
    }
    
    public float findLeastY(){
        float[] yCoords = {SW.getY(), NW.getY(), SE.getY(), NE.getY()};
        return (PerfectBox.findLeastFloatOf(yCoords));
    }
    
    public float findAngleToPerfectBox(PerfectBox pb){
       return 1; 
    }
    
    public static float findGreatestFloatOf(float[] testArr){
        ArrayList<Float> testFloatList = new ArrayList<Float>();
        for(float testFloat : testArr){
            testFloatList.add(testFloat);
        }

        float currentTestFloat;
        float currentTestingListSize = testFloatList.size();
        outerLoop:
        for(int i = 0; i < currentTestingListSize; i++){
            currentTestFloat = testFloatList.get(i);
            testFloatList.remove(testFloatList.get(i));
            currentTestingListSize--;
            innerLoop:  
            for(float testingAgainst : testFloatList){
                if(testingAgainst > currentTestFloat){
                    i--;
                    continue outerLoop;
                }
            }
            return currentTestFloat;
        }
        //System.out.println("findgreatestfloat has gone wrong :(");
        return -1;
    }
    
    public static float findLeastFloatOf(float[] testArr){
        ArrayList<Float> testFloatList = new ArrayList<Float>();
        for(float testFloat : testArr){
            testFloatList.add(testFloat);
        }

        float currentTestFloat;
        float currentTestingListSize = testFloatList.size();
        outerLoop:
        for(int i = 0; i < currentTestingListSize; i++){
            currentTestFloat = testFloatList.get(i);
            testFloatList.remove(testFloatList.get(i));
            currentTestingListSize--;
            innerLoop:  
            for(float testingAgainst : testFloatList){
                if(testingAgainst < currentTestFloat){
                    i--;
                    continue outerLoop;
                }
            }
            return currentTestFloat;
        }
        //System.out.println("findgreatestfloat has gone wrong :(");
        return -1;
    }
    
    public float getWidth(){
        return width;
    }
    
    public float getHeight(){
        return height;
    }
    
    public Coordinates getCenterPoint(){
        return centerPoint;
    }
    
    public Coordinates getSW(){
        return SW;
    }
    
    public Coordinates getNW(){
        return NW;
    }
    
    public Coordinates getSE(){
        return SE;
    }
    
    public Coordinates getNE(){
        return NE;
    }
    
    public Coordinates[] getCorners(){
        Coordinates[] corners = {SW, NW, SE, NE};
        return corners;
    }
    
    public void printCornersForTesting(){
        System.out.println(SW.getX() + ", " + SW.getY());
        System.out.println(NW.getX() + ", " + NW.getY());
        System.out.println(SE.getX() + ", " + SE.getY());
        System.out.println(NE.getX() + ", " + NE.getY());
    }
}
