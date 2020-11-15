package amartinm.draughts.views;

import amartinm.draughts.controllers.InteractorController;
import amartinm.draughts.controllers.InteractorControllersVisitor;
import amartinm.draughts.controllers.PlayController;
import amartinm.draughts.controllers.ResumeController;
import amartinm.draughts.controllers.StartController;

public class View implements InteractorControllersVisitor {

    private StartView startView;
    private PlayView playView;
    private ResumeView resumeView;

    public View(){
        this.startView = new StartView();
        this.playView = new PlayView();
        this.resumeView = new ResumeView();
    }

    public void interact(InteractorController controller) {
        assert controller != null;
        controller.accept(this);
    }

    @Override
    public void visit(StartController startController) {
        assert startController != null;
        this.startView.interact(startController);
    }

    @Override
    public void visit(PlayController playController) {
        assert playController != null;
        this.playView.interact(playController);
    }

    @Override
    public void visit(ResumeController resumeController) {
        assert resumeController != null;
        this.resumeView.interact(resumeController);
    }

}
