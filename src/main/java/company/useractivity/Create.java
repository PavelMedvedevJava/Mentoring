package company.useractivity;

import company.view.DeveloperVIew;

public class Create  implements Activity{
    DeveloperVIew developerVIew = new DeveloperVIew();

    @Override
    public void run() {
        developerVIew.createDeveloper();
    }
}
