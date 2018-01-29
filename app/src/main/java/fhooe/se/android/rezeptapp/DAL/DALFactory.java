package fhooe.se.android.rezeptapp.DAL;

/**
 * Created by Tom on 16.01.2018.
 * basic factory class used for getting the database manager.
 */

public class DALFactory {
    private static DataManager dataManager = new DataManager();

    public static IDataManager GetDataManager(){return dataManager;}
}
