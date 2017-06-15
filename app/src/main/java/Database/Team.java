package Database;



public class Team {

    int _teamNumber;
    int _gearsPlaced;
    int _climbAttempts;
    int _climbed;

    public Team() {
    }



    public Team(int _teamNumber, int _gearsPlaced, int _climbAttempts, int _climbed) {
        this._teamNumber = _teamNumber;
        this._gearsPlaced = _gearsPlaced;
        this._climbAttempts = _climbAttempts;
        this._climbed = _climbed;
    }


    public int getTeamNumber() {
        return _teamNumber;
    }

    public void setTeamNumber(int _teamNumber) {
        this._teamNumber = _teamNumber;
    }

    public int getGearsPlaced() {
        return _gearsPlaced;
    }



    public void setGearsPlaced(int _gearsPlaced) {
        this._gearsPlaced = _gearsPlaced;
    }

    public int getClimbAttempts() {
        return _climbAttempts;
    }

    public void setClimbAttempts(int _climbAttempts) {
        this._climbAttempts = _climbAttempts;
    }

    public int getClimbed() {
        return _climbed;
    }

    public void setClimbed(int _climbed) {
        this._climbed = _climbed;
    }

}
