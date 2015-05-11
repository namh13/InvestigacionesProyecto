package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage s;
	 static MiActor dibujo;

	@Override
	public void create () {
		s = new Stage();
		s.addActor(new Image(new Texture("fondo1.png")));
		s.addActor(new Start());
		dibujo = new MiActor();
		s.addActor(dibujo);
		dibujo.setVisible(false);

		Gdx.input.setInputProcessor(s);

		InputListener keyboardListener;
		keyboardListener = new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keyCode) {
				if (event.getKeyCode() == 22) {
					if(dibujo.getX() < 530)
					dibujo.setX(dibujo.getX() + 10);
				}
				if(event.getKeyCode() == 21) {
					if(dibujo.getX() > 0)
					dibujo.setX(dibujo.getX() - 10);
				}
				if (event.getKeyCode() == 20) {
					if(dibujo.getY() > 0)
					dibujo.setY(dibujo.getY() - 10);
				}
				if (event.getKeyCode() == 19) {
					if(dibujo.getY() < 330)
						dibujo.setY(dibujo.getY() + 10);
				}
				else
				 {
					return false;
				}
				return true;
			}
		};
		s.addListener(keyboardListener);

		try{

			chatClient.clientSocket = new Socket("localhost", 6789);

			Thread t = new Thread(new MyServerListener());
			t.start();

			//chatClient.clientSocket.close();
		}
		catch(Exception e)
		{}
	}

	static public void EnviarServidor(String msj) {
		try {
			DataOutputStream outToServer =
					new DataOutputStream(
							chatClient.clientSocket.getOutputStream());

			outToServer.writeBytes(msj + '\n');
		}
		catch(Exception e)
		{}
	}
	@Override
	public void render () {
		s.draw();
		s.act();
	}
}
