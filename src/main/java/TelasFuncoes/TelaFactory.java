package TelasFuncoes;

import TelasFuncoes.Impl.TelaImpl;

public class TelaFactory {

    int i;
   
  

    public static TelaInterface createTesteImpl(){
        return new TelaImpl();
    }

}

