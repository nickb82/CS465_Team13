package appserver.satellite;

import appserver.job.Job;
import appserver.comm.ConnectivityInfo;
import appserver.job.UnknownToolException;
import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.REGISTER_SATELLITE;
import appserver.job.Tool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import utils.PropertyHandler;

/**
 * Class [Satellite] Instances of this class represent computing nodes that execute jobs by
 * calling the callback method of tool a implementation, loading the tool's code dynamically over a network
 * or locally from the cache, if a tool got executed before.
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Satellite extends Thread {

    private ConnectivityInfo satelliteInfo = new ConnectivityInfo();
    private ConnectivityInfo serverInfo = new ConnectivityInfo();
    private HTTPClassLoader classLoader = null;
    private Hashtable toolsCache = null;

    public Satellite(String satellitePropertiesFile, String classLoaderPropertiesFile, String serverPropertiesFile) {

       String satelliteName;
       int satellitePort;
       
       String serverHost;
       int serverPort;
       
       String codeServerHost;
       int codeServerPort;
       
        // read this satellite's properties and populate satelliteInfo object,
        // which later on will be sent to the server
        // ...
        try 
        {
            Properties satelliteProperties;
            satelliteProperties = new PropertyHandler(satellitePropertiesFile);
            satelliteName = satelliteProperties.getProperty("NAME");
            satellitePort =  Integer.parseInt(satelliteProperties.getProperty("PORT"));
            satelliteInfo.setName(satelliteName);
            satelliteInfo.setPort(satellitePort);

        } 
        
        catch (Exception e) 
        {
            System.err.println("Properties file " + satellitePropertiesFile + " not found, exiting ...");
            System.exit(1);
        }
        
        
        // read properties of the application server and populate serverInfo object
        // other than satellites, the as doesn't have a human-readable name, so leave it out
        // ...
        
        try 
        {
            Properties serverProperties;
            serverProperties = new PropertyHandler(serverPropertiesFile);
            serverHost = serverProperties.getProperty("HOST");
            serverPort =  Integer.parseInt(serverProperties.getProperty("PORT"));
            serverInfo.setHost(serverHost);
            serverInfo.setPort(serverPort);

        }
        catch (Exception e) 
        {
            System.err.println("Properties file " + serverPropertiesFile + " not found, exiting ...");
            System.exit(1);
        }
        
        // read properties of the code server and create class loader
        // -------------------
        // ...
        
        try
        {
           Properties codeServerProperties;
           codeServerProperties = new PropertyHandler(classLoaderPropertiesFile);
           codeServerHost = codeServerProperties.getProperty("HOST");
           codeServerPort = Integer.parseInt(codeServerProperties.getProperty("PORT"));
           
           try 
           {
               classLoader = new HTTPClassLoader(codeServerHost, codeServerPort);
           } 
           catch (NumberFormatException nfe) 
           {
               System.err.println("Wrong Portnumber, using Defaults");
           }
        }
        catch (Exception e) 
        {
            System.err.println("Properties file " + classLoaderPropertiesFile + " not found, exiting ...");
            System.exit(1);
        }

        
        // create tools cache --> <String, tool Object>
        // -------------------
        // ...
        toolsCache = new Hashtable<>();
        
    }

    @Override
    public void run() 
    {
       ServerSocket server;
       Socket socket = null;
       System.out.println(serverInfo.getPort());

        // register this satellite with the SatelliteManager on the server
        // ---------------------------------------------------------------
        // ...
        
        
        // create server socket
        // ---------------------------------------------------------------
        // ...
        try
        {
           server = new ServerSocket(serverInfo.getPort());
           socket = server.accept();
        }
        catch(IOException ioe)
        {
           System.err.println("Could not create server socket");
        }
        
        // start taking job requests in a server loop
        // ---------------------------------------------------------------
        // ...
        while(true)
        {
           new SatelliteThread(socket,this).start();
        }
    }

    // inner helper class that is instanciated in above server loop and processes single job requests
    private class SatelliteThread extends Thread 
    {

        Satellite satellite = null;
        Socket jobRequest = null;
        ObjectInputStream readFromNet = null;
        ObjectOutputStream writeToNet = null;
        Message message = null;

        SatelliteThread(Socket jobRequest, Satellite satellite) {
            this.jobRequest = jobRequest;
            this.satellite = satellite;
        }

        @Override
        public void run() {
            // setting up object streams
            // ...
            try
            {
               readFromNet = new ObjectInputStream(jobRequest.getInputStream());
               writeToNet = new ObjectOutputStream(jobRequest.getOutputStream());
            }
            catch(IOException ioe)
            {
               System.err.println("Could not open streams");
            }
            
            // reading message
            // ...
            try
            {
               message = (Message) readFromNet.readObject();
            }
            catch(Exception err)
            {
               System.err.println("Could not read from ObjectInputStream");
            }
            
            switch (message.getType()) {
                case JOB_REQUEST:
                    // processing job request
                    // ...
                   Job content = (Job) message.getContent();
                   String toolName = content.getToolName();
                   Object jobParam = content.getParameters();
                   
                   //call getToolObject
                   try
                   {
                      Tool toolObject = getToolObject(toolName);
                      Object result = toolObject.go(jobParam);
                      writeToNet.writeObject(result);
                   }
                   catch(Exception err)
                   {
                      System.err.println("Error with creating toolObject");
                   }
                   
                    break;

                default:
                    System.err.println("[SatelliteThread.run] Warning: Message type not implemented");
            }
        }
    }

    /**
     * Aux method to get a tool object, given the fully qualified class string
     * If the tool has been used before, it is returned immediately out of the cache,
     * otherwise it is loaded dynamically
     */
    public Tool getToolObject(String toolClassString) throws UnknownToolException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Tool toolObject = null;
        
        if((toolObject = (Tool)toolsCache.get(toolClassString)) == null)
        {
           System.out.println("\n Tool Object: "  + toolClassString);
           
           if(toolClassString == null)
           {
              System.out.println("toolClassString is null");
              System.exit(-1);
           }
           
           Class<?> toolClass = classLoader.loadClass(toolClassString);
           try
           {
              toolObject = (Tool) toolClass.getDeclaredConstructor().newInstance();
           }
           catch (InvocationTargetException | NoSuchMethodException ex) 
           {
                Logger.getLogger(Satellite.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("[DynCalculator] getToolObject() - InvocationTargetException");
            }
           toolsCache.put(toolClassString, toolObject);
        }
        
        else
        {
           System.out.println("Tool: \"" + toolClassString + "\" already in Cache");
        }
        
        return toolObject;
    }

    public static void main(String[] args) {
        // start the satellite
        Satellite satellite = new Satellite(args[0], args[1], args[2]);
        satellite.run();
    }
}