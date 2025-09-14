package com.busragazel.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

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
	float [] enemyOffset = new float[numberOfEnemies];
	float[] enemyOffset2  =new float[numberOfEnemies];
	float[] enemyOffset3  =new float[numberOfEnemies];

	float distance = 0;
	float enemyVelocity = 2;

	Random random;

	Circle birdCircle;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	ShapeRenderer shapeRenderer;

	int score=0;
	int scoredEnemy=0;

	BitmapFont font;
	BitmapFont font2;


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
		random = new Random();
		birdX = Gdx.graphics.getWidth() / 6;
		birdY = Gdx.graphics.getHeight() / 3;

		shapeRenderer =  new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);


		for(int i = 0 ;i<numberOfEnemies; i++){


			enemyOffset[i] = (random.nextFloat() - 0.5f)*(Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - 200);

			//Her oluşan ufo setinin x'i  kendi distancesine göre ayarlanmış olucak

			enemyX[i] = Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;


			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();



		}

	}

	@Override
	public void render () {
		// Oyun açıldığında sürekli devam eden method

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		//oyun başladığında
		if(gameState == 1){

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 6){
				score ++;

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				}else{
					scoredEnemy = 0;
				}
			}

			//Kullanıcı ekrana dokunduğunda
			if(Gdx.input.justTouched()){
				//Tıklandığında kuşu uçurmak
				velocity = -7;

			}

			for(int i = 0 ; i < numberOfEnemies ; i++){

				if(enemyX[i] < Gdx.graphics.getWidth()/14){

                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f)*(Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - 200);

				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(ufo1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);
				batch.draw(ufo2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset2[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);
				batch.draw(ufo3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset3[i],Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/8);

                enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 , Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight()/ 20, Gdx.graphics.getWidth() / 30);
                enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 , Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight()/ 20, Gdx.graphics.getWidth() / 30);
                enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 , Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight()/ 20, Gdx.graphics.getWidth() / 30);
			}




			if(birdY > 0 ){
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else{
				gameState = 2;
			}


		}else if (gameState == 0){
			//Kullanıcı ekrana dokunduğunda
			if(Gdx.input.justTouched()){
				// Oyunun başladığını anlamak
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"GAME OVER! Tap To Play Again!",500,Gdx.graphics.getHeight() / 2);

			if(Gdx.input.justTouched()){
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;

				for(int i = 0 ;i<numberOfEnemies; i++){


					enemyOffset[i] = (random.nextFloat() - 0.5f)*(Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat()- 0.5f)*(Gdx.graphics.getHeight() - 200);

					//Her oluşan ufo setinin x'i  kendi distancesine göre ayarlanmış olucak

					enemyX[i] = Gdx.graphics.getWidth() - ufo1.getWidth() / 2 + i * distance;


					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();



				}

				velocity = 0;
				score = 0;
				scoredEnemy = 0;
			}
		}


		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/18,Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf("SCORE : " + score),100,200);

		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth()/30,birdY + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);



		for(int i = 0 ; i < numberOfEnemies ; i++){
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 , Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight()/ 20, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 , Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight()/ 20, Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/ 30 , Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight()/ 20, Gdx.graphics.getWidth() / 30);

			// Çarpışma
			if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])){
				gameState = 2;
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
