package GamePackage;

import GamePackage.GameObjects.BossObject;
import GamePackage.GameObjects.PlayerObject;
import utilities.HighScoreHandler;

public class Game extends Model{

    final PlayerObject joe;

    final BossObject purpleBastard;


    public Game(Controller ctrl, HighScoreHandler hs) {
        super(ctrl, hs);
        joe = new PlayerObject(ctrl);
        purpleBastard = new BossObject();
    }

    @Override
    public Game revive() {

        return null;

    }

    @Override
    void updateLoop() {

    }

    @Override
    void setupModel() {

    }

    @Override
    void clearCollections(){
        super.clearCollections();

    }
}
