package com.busragazel.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;

	float birdX = 0;
	float birdY = 0;

	int gameState = 0;

	float velocity = 0f;

	float gravity = 0.1f;


	
	@Override
	public void create () {
		//Oyun açıldığında oluşucak şeyler
		//Çizim aracı init
		batch = new SpriteBatch();
		background = new Texture("bg2.png");
		bird= new Texture("bird.png");

		birdX = Gdx.graphics.getWidth()/6;
		birdY = Gdx.graphics.getHeight()/2;

	}

	@Override
	public void render () {
		// Oyun açıldığında sürekli devam eden method

		//Kullanıcı ekrana dokunduğunda
		if(Gdx.input.justTouched()){
			// Oyunun başladığını anlamak için
			gameState = 1;
		}

		if(gameState == 1){
			if(birdY > 0 || velocity < 0){
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}


		}else{
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}


		batch.begin();

		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/16,Gdx.graphics.getHeight()/10);


		batch.end();


	}
	
	@Override
	public void dispose () {

	}
}
