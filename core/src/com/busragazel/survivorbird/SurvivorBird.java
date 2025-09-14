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
	Texture ufo1;
	Texture ufo2;
	Texture ufo3;

	float birdX = 0;
	float birdY = 0;

	int gameState = 0;

	float velocity = 0;

	float gravity = 0.1f;


	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];

	float distance = 0;

	float enemyVelocity = 2;




	
	@Override
	public void create () {
		//Oyun açıldığında oluşucak şeyler
		//Çizim aracı init
		batch = new SpriteBatch();
		background = new Texture("bg2.png");
		bird= new Texture("bird.png");
		ufo1= new Texture("ufo.png");
		ufo2= new Texture("ufo.png");
		ufo3= new Texture("ufo.png");

		//Ufolar arasında ekranın yarısı kadar mesafe eklemek için oluşturuldu
		distance = Gdx.graphics.getWidth() / 2;



		birdX = Gdx.graphics.getWidth() / 6;
		birdY = Gdx.graphics.getHeight() / 3;


		for(int i = 0 ;i<numberOfEnemies; i++){

			//Her oluşan ufo setinin x'i  kendi distancesine göre ayarlanmış olucak

			enemyX[i] = Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;

		}

	}

	@Override
	public void render () {
		// Oyun açıldığında sürekli devam eden method

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		//oyun başladığında
		if(gameState == 1){

			//Kullanıcı ekrana dokunduğunda
			if(Gdx.input.justTouched()){
				//Tıklandığında kuşu uçurmak
				velocity = -7;

			}

			for(int i = 0 ; i < numberOfEnemies ; i++){

				if(enemyX[i] < Gdx.graphics.getWidth()/14){
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;
				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(ufo1,enemyX[i],50,Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);
				batch.draw(ufo2,enemyX[i],200,Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);
				batch.draw(ufo3,enemyX[i],400,Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);


			}




			if(birdY > 0 || velocity < 0){
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}


		}else{
			//Kullanıcı ekrana dokunduğunda
			if(Gdx.input.justTouched()){
				// Oyunun başladığını anlamak
				gameState = 1;
			}
		}


		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);


		batch.end();


	}
	
	@Override
	public void dispose () {

	}
}
