package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.db.PrieteniiDbRepository;
import socialnetwork.repository.db.UtilizatorDbRepository;
import socialnetwork.service.PrietenieService;
import socialnetwork.service.UtilizatorService;
import socialnetwork.service.UtilizatoriPrieteniiService;
import socialnetwork.ui.Console;


public class Main {
    public static void main(String[] args) {

        Validator<Utilizator> validatorUtilizator = new UtilizatorValidator();
        Validator<Prietenie> validatorPrietenie = new PrietenieValidator();

//        String utilizatorifileName = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
//        //Repository<Long, Utilizator> utilizatorInMemoryRepository = new InMemoryRepository<>(validator);
//        Repository<Long, Utilizator> userFileRepository = new UtilizatorFileRepository(utilizatorifileName, validatorUtilizator);
//        //Repository<Tuple<Long, Long>, Prietenie> prietenieInMemoryRepository = new InMemoryRepository<>(validatorPrietenie);
//        String prietenieFileName = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.prietenii");
//        Repository<Tuple<Long, Long>, Prietenie> prietenieFileRepository = new PrietenieFileRepository(
//                prietenieFileName, validatorPrietenie);
//
//        UtilizatorService utilizatorService = new UtilizatorService(userFileRepository);
//        //UtilizatorService utilizatorService = new UtilizatorService(utilizatorInMemoryRepository);
//        PrietenieService prietenieService = new PrietenieService(prietenieFileRepository);
//        //PrietenieService prietenieService = new PrietenieService(prietenieInMemoryRepository);
//        UtilizatoriPrieteniiService utilizatoriPrieteniiService = new
//                UtilizatoriPrieteniiService(utilizatorService, prietenieService);


        final String url = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        final String username = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        final String password = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");

        Repository<Long, Utilizator> utilizatorDbRepository =
                new UtilizatorDbRepository(url, username, password, validatorUtilizator);
        UtilizatorService utilizatorService = new UtilizatorService(utilizatorDbRepository);

        Repository<Tuple<Long, Long>, Prietenie>  prietenieDbRepository =
                new PrieteniiDbRepository(url, username, password, validatorPrietenie);
        PrietenieService prietenieService = new PrietenieService(prietenieDbRepository);

        UtilizatoriPrieteniiService utilizatoriPrieteniiService = new
                UtilizatoriPrieteniiService(utilizatorService, prietenieService);

        Console console = new Console(utilizatorService, prietenieService, utilizatoriPrieteniiService);
        console.run_console();
    }
}
