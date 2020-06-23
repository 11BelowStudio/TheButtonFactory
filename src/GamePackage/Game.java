package GamePackage;

import utilities.HighScoreHandler;

public class Game extends Model{


    public Game(Controller ctrl, HighScoreHandler hs) {
        super(ctrl, hs);
    }

    @Override
    public Model revive() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    void setupModel() {

    }
}
