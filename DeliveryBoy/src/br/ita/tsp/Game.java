package br.ita.tsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import br.ita.tsp.db.Player;
import br.ita.tsp.db.PlayerComparator;
import br.ita.tsp.graph.Grafo;
import br.ita.tsp.graph.Vertice;
import br.ita.tsp.media.GameMediaManager;
import br.ita.tsp.util.Constants;
import br.ita.tsp.util.GameUtils;
import br.ita.tsp.util.MathUtils;

/**
 * @author CT-208
 * @since 22:00:20 - 15.05.2011
 */
public class Game extends BaseGameActivity {
	public static final String PREFS_NAME = "DeliveryBoySettings";

	// constantes do jogo
	private static int CAMERA_WIDTH = -1;
	private static int CAMERA_HEIGHT = -1;

	// andengine games
	private Camera mCamera;
	private Scene scene;
	private PhysicsWorld mPhysicsWorld;

	// andengine general fonts
	private Font mFont;
	private Texture mFontTexture;

	// postman variables
	private Texture postmanTexture;
	private TiledTextureRegion postmanTextureRegion;
	private TiledSprite postman;
	private Body postmanBody;
	private Texture mVertice;
	private TextureRegion mVerticeTextureRegion;

	private Texture mChegada;
	private TextureRegion mChegadaTextureRegion;

	private Texture mBackgroundTexture;
	private TextureRegion mBackgroundTextureRegion;

	// jostick
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;

	// objetos de visualizacao do score
	private ChangeableText combText;
	private ChangeableText scoreText;

	// Grafo
	private Grafo grafo;
	private SpriteVertice verticesAtivos = null;

	// game logic
	private float distanciaTotal;
	private long tempo_inicial;
	private boolean WINNER = false;
	private float combustivel;
	private int score = 0;
	private int user_record;
	private Rectangle[] progressBar;
	// private Rectangle[] vertices_no_mapa;
	private Sprite[] vertices_no_mapa;
	private Sprite chegada;

	// posicao do objeto
	private float lastPositionX = 0.0f;
	private float lastPositionY = 0.0f;

	// music control
	private GameMediaManager mediaManager;
	private Music mMusic;

	// system controllers
	private boolean control = false;
	private Handler handler = null;

	// dialog variables
	private static int VAR_DIALOG = 0;
	private static final int WIN = 1;
	private static final int LOOSE = -1;

	@Override
	public Engine onLoadEngine() {
		DisplayMetrics mDM = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDM);
		CAMERA_WIDTH = mDM.widthPixels;
		CAMERA_HEIGHT = mDM.heightPixels;

		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {
		TextureRegionFactory.setAssetBasePath("gfx/");

		this.mFontTexture = new Texture(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 18, true, Color.WHITE);

		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);

		this.mBackgroundTexture = new Texture(1024, 512, TextureOptions.DEFAULT);
		SharedPreferences settings = getSharedPreferences(Menu.PREFS_NAME, 0);

		boolean theme = settings.getBoolean("mode", true);
		if (theme == true)
			this.mBackgroundTextureRegion = TextureRegionFactory
					.createFromAsset(this.mBackgroundTexture, this, "maps.png",
							0, 0);

		else
			this.mBackgroundTextureRegion = TextureRegionFactory
					.createFromAsset(this.mBackgroundTexture, this,
							"background_game.png", 0, 0);

		this.postmanTexture = new Texture(128, 16,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.postmanTextureRegion = TextureRegionFactory.createTiledFromAsset(
				this.postmanTexture, this, "vehicles.png", 32, 0, 6, 1);

		this.mVertice = new Texture(32, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mVerticeTextureRegion = TextureRegionFactory.createFromAsset(
				this.mVertice, this, "house.png", 0, 0);

		this.mChegada = new Texture(64, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mChegadaTextureRegion = TextureRegionFactory.createFromAsset(
				this.mChegada, this, "finish.png", 0, 0);

		this.mOnScreenControlTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);

		try {
			MusicFactory.setAssetBasePath("mfx/");
			mMusic = MusicFactory.createMusicFromAsset(
					this.mEngine.getMusicManager(), this, "traffic.mp3");
			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}

		this.mEngine.getTextureManager().loadTextures(this.mBackgroundTexture,
				this.postmanTexture, this.mOnScreenControlTexture,
				this.mVertice, this.mChegada);

	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene(6);
		scene.setBackground(new ColorBackground(0f, 0f, 0f));
		scene.setBackgroundEnabled(true);
		scene.getChild(Constants.LAYER_BACKGROUND).attachChild(
				new Sprite(0, 0, this.mBackgroundTextureRegion));

		mPhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0, 0), false,
				8, 1);

		startComponents();

		/* The actual collision-checking. */
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				colision();
			}
		});

		scene.registerUpdateHandler(this.mPhysicsWorld);
		MusicFactory.setAssetBasePath("mfx/");

		SharedPreferences set = getSharedPreferences(Menu.PREFS_NAME, 0);
		boolean sound = set.getBoolean("sound", true);

		if (sound == true)
			Game.this.playerMedia();

		return scene;
	}

	@Override
	public void onLoadComplete() {
	}

	/******************************* INICIALIZACAO *******************************/

	private void startComponents() {
		initGeneralComponents();
		initGameBorders();
		initPostman();
		initJostickControls();
		initCidades();
		initScore();
		initProgressBar();
	}

	private void initGeneralComponents() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa os componentes gerais");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		SharedPreferences set = getSharedPreferences(Menu.PREFS_NAME, 0);
		boolean difficult = set.getBoolean("difficult", true);

		int user_difficult = 0;// NORMAL
		if (difficult == false)
			user_difficult = 1;// HARD

		grafo = Grafo.getInstance(user_difficult);

		distanciaTotal = 0;

		// inicializa o tempo
		tempo_inicial = System.currentTimeMillis();

		// inicializa o grafo
		grafo.initGrafo(WINNER);

		// reinicializa o status do game
		WINNER = false;

		Log.d("inicializacao", "O nivel do jogo eh " + grafo.getFase());
		Log.d("inicializacao",
				"O numero de cidades eh " + grafo.getNumeroCidades());

		// calcula o combustivel
		combustivel = grafo.getCombustivel();
		Log.d("inicializacao", "O combustivel existente eh " + combustivel);
		Log.d("inicializacao", "O TSP eh " + grafo.getTSP());

		verticesAtivos = new SpriteVertice(grafo.getCidades());

		mediaManager = GameMediaManager.getInstance(this);

		mediaManager.stopMusic();

		control = false;

	}

	private void initGameBorders() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa as bordas");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		final Shape bottomOuter = new Rectangle(140, CAMERA_HEIGHT - 2,
				CAMERA_WIDTH - 140, 2);
		final Shape topOuter = new Rectangle(140, 0, CAMERA_WIDTH - 140, 2);
		final Shape leftOuter = new Rectangle(140, 0, 2, CAMERA_HEIGHT);
		final Shape rightOuter = new Rectangle(CAMERA_WIDTH - 2, 0, 2,
				CAMERA_HEIGHT);

		bottomOuter.setColor(0, 255, 255);
		topOuter.setColor(0, 255, 255);
		leftOuter.setColor(0, 255, 255);
		rightOuter.setColor(0, 255, 255);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0,
				0.5f, 0.5f);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, bottomOuter,
				BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, topOuter,
				BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, leftOuter,
				BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, rightOuter,
				BodyType.StaticBody, wallFixtureDef);

		final IEntity firstChild = scene.getChild(Constants.LAYER_BORDERS);
		firstChild.attachChild(bottomOuter);
		firstChild.attachChild(topOuter);
		firstChild.attachChild(leftOuter);
		firstChild.attachChild(rightOuter);
	}

	private void initPostman() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa o Postman");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		// A posicao do postman eh definida aqui
		this.postman = new TiledSprite(8 * Constants.GRAFO_CONST,
				1 * Constants.GRAFO_CONST, Constants.POSTMAN_SIZE,
				Constants.POSTMAN_SIZE, this.postmanTextureRegion);
		this.postman.setCurrentTileIndex(3);

		final FixtureDef carFixtureDef = PhysicsFactory.createFixtureDef(1,
				0.5f, 0.5f);
		this.postmanBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld,
				this.postman, BodyType.DynamicBody, carFixtureDef);

		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
				this.postman, this.postmanBody, true, false));

		scene.getChild(Constants.LAYER_POSTMAN).attachChild(this.postman);
	}

	private void initJostickControls() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa Josticks");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		final DigitalOnScreenControl digitalOnScreenControl = new DigitalOnScreenControl(
				0, CAMERA_HEIGHT
						- this.mOnScreenControlBaseTextureRegion.getHeight(),
				this.mCamera, this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f,
				new IOnScreenControlListener() {

					@Override
					public void onControlChange(
							final BaseOnScreenControl pBaseOnScreenControl,
							final float pValueX, final float pValueY) {

						float newPositionX = lastPositionX + pValueX * 1;// incrementa
																			// de
																			// .500
						float newPositionY = lastPositionY + pValueY * 1;

						if ((lastPositionX != newPositionX)
								|| (lastPositionY != newPositionY)) {

							distanciaTotal += 4;

							// eu ando
							final Body carBody = postmanBody;
							Vector2 velocity = Vector2Pool.obtain(pValueX * 1,
									pValueY * 1);
							carBody.setLinearVelocity(velocity);
							Vector2Pool.recycle(velocity);

							// vira o carro conforme comando joystick
							final float rotationInRad = (float) Math.atan2(
									-pValueX, pValueY);
							carBody.setTransform(carBody.getWorldCenter(),
									rotationInRad);

							Game.this.postman.setRotation(MathUtils
									.radToDeg(rotationInRad));

							// atualiza o mProgress
							combText.setText("Dist: "
									+ new Float(distanciaTotal).intValue()
									+ " Km");

							updateProgressBar();

							// atualiza o lastPosition
							lastPositionX = carBody.getPosition().x;
							lastPositionY = carBody.getPosition().y;

						} else {
							// Log.d(ANDAR, "Nao andou");
							final Body carBody = postmanBody;
							Vector2 velocity = Vector2Pool.obtain(pValueX * 0,
									pValueY * 0);

							carBody.setLinearVelocity(velocity);
							Vector2Pool.recycle(velocity);
							// updateProgressBar(pScene);
						}
					}

				});
		digitalOnScreenControl.getControlBase().setBlendFunction(
				GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		digitalOnScreenControl.getControlBase().setAlpha(0.5f);
		digitalOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(digitalOnScreenControl);
	}

	private void initCidades() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa cidades no mapa");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		vertices_no_mapa = new Sprite[this.verticesAtivos
				.getNumeroVerticesNaoVisitados()];

		for (int i = 1; i <= this.verticesAtivos
				.getNumeroVerticesNaoVisitados(); i++) {
			Vertice vert = this.verticesAtivos.getVertice(i);
			float px = vert.getPosX();
			float py = vert.getPosY();

			final Sprite Vertice = new Sprite(px, py,
					this.mVerticeTextureRegion);

			scene.getChild(Constants.LAYER_VERTICES_MAPA).attachChild(Vertice);
			vertices_no_mapa[i - 1] = Vertice;
		}
		// cria o ponto de chegada
		chegada = new Sprite(5 * Constants.GRAFO_CONST,
				1 * Constants.GRAFO_CONST, this.mChegadaTextureRegion);

		scene.getChild(Constants.LAYER_VERTICES_MAPA).attachChild(chegada);

	}

	private void initScore() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa o score");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		/* The ScoreText showing how many points the pEntity scored. */

		this.scoreText = new ChangeableText(10, 20, this.mFont, "Score: 0000",
				"Score:  XXXX".length());

		this.combText = new ChangeableText(10, 60, this.mFont, "Dist: 0000 km",
				"Dist: XXXX km".length());

		// atualiza o mProgress
		scoreText.setText("Score: " + new Float(score).intValue());

		scene.getChild(Constants.LAYER_SCORE).attachChild(this.combText);
		scene.getChild(Constants.LAYER_SCORE).attachChild(this.scoreText);
	}

	private void initProgressBar() {
		Log.d("inicializacao", "---------------------------------------------------------------------------------");
		Log.d("inicializacao", "Inicializa o ProgressBar");
		Log.d("inicializacao", "---------------------------------------------------------------------------------");

		Text progressText = new Text(10, 100, mFont, "Fuel ");
		/*
		 * progressText.setBlendFunction(GL10.GL_SRC_ALPHA,
		 * GL10.GL_ONE_MINUS_SRC_ALPHA); progressText.setAlpha(0.5f);
		 * progressText.setScale(0.5f);
		 */
		scene.getLastChild().attachChild(progressText);

		this.progressBar = new Rectangle[4];
		int j = 49;
		for (int i = 0; i < 4; i++) {
			Rectangle progPos = new Rectangle(j, 101, 16, 16);
			progPos.setColor(1, 0.8f, 0);
			scene.getLastChild().attachChild(progPos);
			this.progressBar[i] = progPos;
			j += 16;
		}

	}

	/******************************* LOGIC COMANDS E LISTENINGS *******************************/

	private void colision() {
		Sprite targetSprite = null;

		for (int i = 0; i < this.vertices_no_mapa.length; i++) {
			targetSprite = this.vertices_no_mapa[i];
			if (postman.collidesWith(targetSprite)) {
				float x = targetSprite.getX();
				float y = targetSprite.getY();

				boolean result = verticesAtivos.visitaVertice(x, y);

				if (result == true) {
					Log.d("game", "pinta vertice");
					// targetRectangle.setColor(0, 1, 0);
					// scene.getLastChild().detachChild(targetRectangle);
					scene.getChild(Constants.LAYER_VERTICES_MAPA).detachChild(
							targetSprite);
					updateProgressBar();
					updateScore();
				}

				return;
			} else if (postman.collidesWith(chegada)) {
				int not_visitados = Game.this.verticesAtivos
						.getNumeroVerticesNaoVisitados();
				if ((control == false)
						&& ((distanciaTotal >= 0.9 * grafo.getTSP()) || (not_visitados == 0)))
					finishGame(true);// win
			}
		}
	}

	private void updateScore() {
		score = score + 50;
		this.scoreText.setText("Score:  " + new Integer(score).toString());
	}

	private void updateProgressBar() {
		if ((distanciaTotal >= (0.25 * combustivel))
				&& (distanciaTotal < (0.5 * combustivel))) {
			updateProgressBar(75);
		}

		else if ((distanciaTotal >= (0.5 * combustivel))
				&& (distanciaTotal < (0.75 * combustivel))) {
			updateProgressBar(50);
		}

		else if ((distanciaTotal >= (0.75 * combustivel))
				&& (distanciaTotal < (combustivel))) {
			updateProgressBar(25);
		}

		else if ((distanciaTotal >= (combustivel))) {
			updateProgressBar(0);
			if ((control == false))
				finishGame(false);
		}
	}

	private void updateProgressBar(int load) {
		if (load == 75)
			this.progressBar[3].setColor(0, 0, 0);
		else if (load == 50)
			this.progressBar[2].setColor(0, 0, 0);
		else if (load == 25)
			this.progressBar[1].setColor(0, 0, 0);
		else if (load == 0)
			this.progressBar[0].setColor(0, 0, 0);
	}

	private void finishGame(boolean WIN) {
		if (WIN == true) {
			// set o status como true
			control = true;
			WINNER = true;

			VAR_DIALOG = Game.WIN;

			Log.d("game", "Nivel vencido");
			int score_local = new Float(GameUtils.calculateScore(tempo_inicial,
					grafo.getTSP(), distanciaTotal, grafo.getFase()))
					.intValue();

			score += score_local;
			this.user_record = score;
			Log.d("game", "Score final eh " + score);

			mediaManager.playMusic(WINNER);
			apresentaDialog();
		}

		else {
			VAR_DIALOG = Game.LOOSE;
			WINNER = false;
			control = true;
			score = user_record;
			Log.d("game", "Nivel Perdido");
			Log.d("game", "Score final eh " + score);

			// para o player principal
			mediaManager.playMusic(WINNER);
			apresentaDialog();
		}

	}

	private void apresentaDialog() {
		// apresenta a mensagem na tela
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				handler = new Handler();
				showDialog(VAR_DIALOG);
				Message message = handler.obtainMessage();
				handler.dispatchMessage(message);
				Looper.loop();
			}
		}).start();
	}

	@Override
	protected Dialog onCreateDialog(final int pID) {
		switch (pID) {
		case WIN:
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("You win!!")
					.setCancelable(false)
					.setMessage(
							"Your score is :\n" + Game.this.user_record
									+ "\nPress OK to continue ...")
					.setPositiveButton(android.R.string.ok,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface pDialog,
										int pWhich) {
									Game.this.mEngine.setScene(Game.this
											.onLoadScene());
								}
							})
					.setNegativeButton(android.R.string.cancel,
							new OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface pDialog,
										final int pWhich) {
									Game.this.finish();
								}
							}).create();
		case LOOSE:
			final EditText playerNameText = new EditText(this);
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("You loose!\n Put your name in the box!!")
					.setCancelable(false).setView(playerNameText)
					.setPositiveButton("Record & Leave", new OnClickListener() {
						@Override
						public void onClick(final DialogInterface pDialog,
								final int pWhich) {
							String name = playerNameText.getText().toString();
							saveIntoGalleryRecords(name, user_record);
							Game.this.finish();
						}
					}).setNegativeButton("Try again!", new OnClickListener() {
						@Override
						public void onClick(final DialogInterface pDialog,
								final int pWhich) {
							mEngine.setScene(Game.this.onLoadScene());
						}
					}).create();

		default:
			return super.onCreateDialog(pID);
		}
	}

	private void saveIntoGalleryRecords(String name, int user_record) {
		if (user_record == 0)
			return;

		else {
			List<Player> players = new ArrayList<Player>(6);

			SharedPreferences set = getSharedPreferences(Menu.PREFS_NAME, 0);
			// imprime no sharedPreferences
			SharedPreferences.Editor editor = set.edit();


			String score = set.getString("score1", "--------------------");
			if (score.equals("--------------------") == false) {
				String scores[] = Score.parsePlayerString(score);
				Player pl = new Player(scores[0],
						new Integer(scores[1]).intValue());
				players.add(pl);
			}

			score = set.getString("score2", "--------------------");
			if (score.equals("--------------------") == false) {
				String scores[] = Score.parsePlayerString(score);
				Player pl = new Player(scores[0],
						new Integer(scores[1]).intValue());
				players.add(pl);
			}

			score = set.getString("score3", "--------------------");
			if (score.equals("--------------------") == false) {
				String scores[] = Score.parsePlayerString(score);
				Player pl = new Player(scores[0],
						new Integer(scores[1]).intValue());
				players.add(pl);
			}

			score = set.getString("score4", "--------------------");
			if (score.equals("--------------------") == false) {
				String scores[] = Score.parsePlayerString(score);
				Player pl = new Player(scores[0],
						new Integer(scores[1]).intValue());
				players.add(pl);
			}

			score = set.getString("score5", "--------------------");
			if (score.equals("--------------------") == false) {
				String scores[] = Score.parsePlayerString(score);
				Player pl = new Player(scores[0],
						new Integer(scores[1]).intValue());
				players.add(pl);
			}

			score = set.getString("score6", "--------------------");
			if (score.equals("--------------------") == false) {
				String scores[] = Score.parsePlayerString(score);
				Player pl = new Player(scores[0],
						new Integer(scores[1]).intValue());
				players.add(pl);
			}

			if(players.size()==0){
				editor.putString("score1", name+ "++++" + user_record);
			}
			
			else {
				// ordena array
				Collections.sort(players, new PlayerComparator());
				Player player = Collections.min(players, new PlayerComparator());
				if (player.getRecord() <= user_record) {
					player.setName(name);
					player.setRecord(user_record);
				}

				
				for (int i = 0; i < players.size(); i++) {
					Player pl = players.get(i);
					String name_pl = pl.getName();
					int rec_pl = pl.getRecord();

					editor.putString("score" + i, name_pl + "++++" + rec_pl);
				}

				
			}
			editor.commit();

		}
	}

	private void playerMedia() {
		if (mMusic.isPlaying() == false) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					Handler handler = new Handler();
					mMusic.setLooping(true);
					mMusic.play();

					Message message = handler.obtainMessage();
					handler.dispatchMessage(message);
					Looper.loop();
				}
			}).start();

		}
	}

}
