package company.userActivity;

import company.view.DeveloperVIew;

public class Show implements Activity {
    DeveloperVIew developerVIew = new DeveloperVIew();

    @Override
    public void run() {
        developerVIew.showAllDevelopers();
    }
}
