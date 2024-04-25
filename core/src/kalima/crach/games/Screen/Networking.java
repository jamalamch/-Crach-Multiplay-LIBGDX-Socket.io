package kalima.crach.games.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.NetJavaServerSocketImpl;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;

import kalima.crach.games.Assest;
import kalima.crach.games.connection.DataToSocket;
import kalima.crach.games.connection.DataToSocket.State;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

public class Networking implements Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Stage stage;
    private Label labelDetails;
    private Label labelMessage;
    private TextButton button;
    private TextArea textIPAddress;
    private TextArea textMessage;
    private boolean connection;
    
    private int Youport = 9020;
    private int Serport = 9020;

    // Pick a resolution that is 16:9 but not unreadibly small
    
    public final static float VIRTUAL_SCREEN_HEIGHT = 460;
    public final static float VIRTUAL_SCREEN_WIDTH = 240;
    
    
    private NetJavaServerSocketImpl Server ;
     private java.net.Socket socket;
     
     
     public Networking() {
         new Thread(new Runnable(){
 			@Override
             public void run() {
                 ServerSocketHints serverSocketHint = new ServerSocketHints();
      	        byte T[] = new byte[128];
                 serverSocketHint.acceptTimeout = 0;
             	Server = new NetJavaServerSocketImpl(Protocol.TCP, Youport, serverSocketHint);
             	while(true){
                     try {
                                 Socket socket = Server.accept(null);
                     	         int t = socket.getInputStream().read(T);
                     	         System.out.println(" Byte is "+t);
                     	         byte code [] = new byte[t];
                     	         while(t>0) {t--;code [t] = T[t]; }
                     	         DataToSocket Data = getingData(code);
                                 System.out.println(Data.toString()) ;
                                 textMessage.setText(Data.id);
                                 connection = true;
                                 socket.dispose();
 					} catch (IOException e) {
 					        	System.out.println("errour");
 					}

                 }
             }
         }).start();
	}
    
	@Override
	public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        
        // Load our UI skin from file.  Once again, I used the files included in the tests.
        // Make sure default.fnt, default.png, uiskin.[atlas/json/png] are all added to your assets
        stage = new Stage();
        // Wire the stage to receive input, as we are using Scene2d in this example
        Gdx.input.setInputProcessor(stage);

        
        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example

        // Vertical group groups contents vertically.  I suppose that was probably pretty obvious
        VerticalGroup vg = new VerticalGroup().space(3).pad(5).fill();//.space(2).pad(5).fill();//.space(3).reverse().fill();
        // Set the bounds of the group to the entire virtual display
        vg.setBounds(0, 0, VIRTUAL_SCREEN_WIDTH, VIRTUAL_SCREEN_HEIGHT);
        
        // Create our controls
        labelDetails = new Label("localhost",Assest.Style);
        labelMessage = new Label("Hello world",Assest.Style); 
        button = new TextButton("Send message",Assest.Style);
        textIPAddress = new TextArea("127.0.0.1",Assest.Style);
        textMessage = new TextArea("",Assest.Style);

        // Add them to scene
        vg.addActor(labelDetails);
        vg.addActor(labelMessage);
        vg.addActor(textIPAddress);
        vg.addActor(textMessage);
        vg.addActor(button);
        
        // Add scene to stage
        stage.addActor(vg);
        
        // Setup a viewport to map screen to a 480x640 virtual resolution
        // As otherwise this is way too tiny on my 1080p android phone.
        stage.getCamera().position.set(VIRTUAL_SCREEN_WIDTH/2,VIRTUAL_SCREEN_HEIGHT/2,0);
        
        // Now we create a thread that will listen for incoming socket connections
        
        
        
        
 // And, start the thread running
        
        
		
				try {
					socket = new java.net.Socket("localhost", Serport);
                	DataToSocket Data = new DataToSocket("its me", 12, 4, 55, State.New_Player);
                	socket.getOutputStream().write(sendingMessageData(Data));
                	connection = true;
                	socket.close();
                } catch (UnknownHostException e1) {
					System.out.println(" Adress unkon");
				} catch (IOException e1) {
					System.out.println(" erour port");
				}

        button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(connection)
			    try {
					        socket = new java.net.Socket("localhost", Serport);
	                    	DataToSocket Data = new DataToSocket("11fje", 12, 4, 55, State.Destor_Box);
	                    	socket.getOutputStream().write(sendingMessageData(Data));
	                    	socket.getOutputStream().flush();
	                    	socket.close();
	            }
				 catch (UnknownHostException e1) {
					System.out.println(" Adress unkon");
				} catch (IOException e1) {
					System.out.println(" erour port");
				}
            	else
            		System.out.print("no connection");
            		
            }
        }
        );
    }

	public byte[] sendingMessageData(DataToSocket Data) {
		final Json json = new Json();
		return json.toJson(Data,DataToSocket.class).getBytes();
	}
	public DataToSocket getingData(byte[] data) {
		final Json json = new Json();
		String Data = new String(DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary(data)));
		return json.fromJson(DataToSocket.class,Data);
	}
    @Override
    public void dispose() {
        batch.dispose();
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }



	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stage.draw();
        batch.end();		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}