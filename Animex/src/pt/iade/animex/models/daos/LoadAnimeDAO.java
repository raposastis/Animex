package pt.iade.animex.models.daos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import pt.iade.animex.WindowManager;
import pt.iade.animex.models.Anime;
import pt.iade.animex.models.Dialog;
/**
 * Class responsavel por todas as queries para carregar os Animes.
 */

public class LoadAnimeDAO {
	public static int anime_id = 0;
	public static void loadAnime(String procurarAnime,ListView<Anime> ListViewAnimes) {	
		ObservableList<Anime> animeList = FXCollections.observableArrayList();	
		try {
			//%or% Finds any values that have "or" in any position
			PreparedStatement statement = DBConnector.getConnection()
					.prepareStatement("select * from Animes WHERE nome LIKE ?");
			statement.setString(1, procurarAnime + "%");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				int anime_id = results.getInt("anime_id");
				String nome= results.getString("nome");
				String link= results.getString("link");
				String data= results.getString("data");
				int episodes = results.getInt("episodes");
				float score = results.getFloat("score");
				int seasons = results.getInt("seasons");
				String autor= results.getString("autor");
				String synopsis= results.getString("synopsis");
				String genre = results.getString("genre");
                InputStream imagem = results.getBinaryStream("imagem");
                byte[] imagem1 = getBytesFromInputStream(imagem);
                animeList.add(new Anime(anime_id,nome,link,data,episodes,score,seasons,autor,synopsis,genre,imagem1));
			}
			ListViewAnimes.setCellFactory(new Callback<ListView<Anime>,ListCell<Anime>>() {

				@Override
				public ListCell<Anime> call(ListView<Anime> arg0) {
					ListCell<Anime> cell = new ListCell<Anime>() {
						@Override
						protected void updateItem(Anime anime, boolean btl) {
							super.updateItem(anime, btl);
							if (anime != null) {
								
								Image img = new Image(new ByteArrayInputStream(anime.getImagem()));
								ImageView imgview = new ImageView(img);
								imgview.setFitHeight(80);
								imgview.setFitWidth(50);
								
								Button btnAdicionar = new Button("Mais informa��o");
								btnAdicionar.setOnAction(new EventHandler<ActionEvent>() {
						            @Override  //Metodo para abrir o openInfoAnime
						            public void handle(ActionEvent event) {  
						            	anime_id = anime.getAnime_id();
						            	WindowManager.openinfoAnime();
						            }
						        });
								
								 // Vai criar uma Box para colocar a imagem, nome e botao no centro do lado esquerdo

								HBox hBox = new HBox(5);
			                    hBox.setAlignment(Pos.CENTER_LEFT); 	
			
			                    hBox.getChildren().addAll(
			                    		imgview,
			                            new Label(anime.getNome()),
			                    		new Label("",btnAdicionar));

			                    
			                    setGraphic(hBox);
			                    
							}
						}
					};
					return cell;
				}
				
			});
			ListViewAnimes.setItems(animeList);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void loadAnimeContinuarVer(int user_id, String animePesquisado, ListView<Anime> listview) {
		ObservableList<Anime> animeList = FXCollections.observableArrayList();
		
		try {
			//%or% Finds any values that have "or" in any position
			PreparedStatement statement = DBConnector.getConnection().prepareStatement("select A.anime_id, A.nome,A.autor, A.data, A.score, A.episodes, A.seasons, A.genre, A.synopsis, A.link, A.imagem from animes A, continuarver C where A.anime_id = C.anime_id and C.user_id = ? and A.nome like ?");
			statement.setInt(1, user_id);
			statement.setString(2, animePesquisado + "%");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				
				int anime_id = results.getInt("anime_id");
				String nome= results.getString("nome");
				String link= results.getString("link");
				String data= results.getString("data");
				int episodes = results.getInt("episodes");
				float score = results.getFloat("score");
				int seasons = results.getInt("seasons");
				String autor= results.getString("autor");
				String synopsis= results.getString("synopsis");
				String genre = results.getString("genre");
                InputStream imagem = results.getBinaryStream("imagem");
                byte[] imagem1 = getBytesFromInputStream(imagem);
                
                animeList.add(new Anime(anime_id,nome,link,data,episodes,score,seasons,autor,synopsis,genre,imagem1));
			}
			
			listview.setCellFactory(new Callback<ListView<Anime>,ListCell<Anime>>() {

				@Override
				public ListCell<Anime> call(ListView<Anime> arg0) {
					ListCell<Anime> cell = new ListCell<Anime>() {
						@Override
						protected void updateItem(Anime anime, boolean btl) {
							super.updateItem(anime, btl);
							if (anime != null) {
								
								Image img = new Image(new ByteArrayInputStream(anime.getImagem()));
								ImageView imgview = new ImageView(img);;
								imgview.setFitHeight(80);
								imgview.setFitWidth(50);
								
								Button btnAdicionar = new Button("Mais informa��o");
								btnAdicionar.setOnAction(new EventHandler<ActionEvent>() {
						            @Override  //Metodo para abrir o openInfoAnime
						            public void handle(ActionEvent event) {  
						            	
						              	anime_id = anime.getAnime_id();
						            	WindowManager.openinfoAnime();
						            }
						        });
								
								 

								HBox hBox = new HBox(5);
			                    hBox.setAlignment(Pos.CENTER_LEFT); 	
			
			                    hBox.getChildren().addAll(
			                    		imgview,
			                            new Label(anime.getNome()),
			                    		new Label("",btnAdicionar));

			                    
			                    setGraphic(hBox);
								
							}
						}
					};
					return cell;
				}
				
			});
			listview.setItems(animeList);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void loadAnimeVoltarVer(int user_id, String animePesquisado, ListView<Anime> listview) {
		ObservableList<Anime> animeList = FXCollections.observableArrayList();
		
		try {
			//%or% Finds any values that have "or" in any position
			PreparedStatement statement = DBConnector.getConnection().prepareStatement("select A.anime_id, A.nome,A.autor, A.data, A.score, A.episodes, A.seasons, A.genre, A.synopsis, A.link, A.imagem from animes A, voltarver B where A.anime_id = B.anime_id and B.user_id = ? and A.nome like ?");
			statement.setInt(1, user_id);
			statement.setString(2, animePesquisado + "%");
			ResultSet results = statement.executeQuery();
			while(results.next()){
				
				int anime_id = results.getInt("anime_id");
				String nome= results.getString("nome");
				String link= results.getString("link");
				String data= results.getString("data");
				int episodes = results.getInt("episodes");
				float score = results.getFloat("score");
				int seasons = results.getInt("seasons");
				String autor= results.getString("autor");
				String synopsis= results.getString("synopsis");
				String genre = results.getString("genre");
                InputStream imagem = results.getBinaryStream("imagem");
                byte[] imagem1 = getBytesFromInputStream(imagem);
                
                animeList.add(new Anime(anime_id,nome,link,data,episodes,score,seasons,autor,synopsis,genre,imagem1));
			}
			
			listview.setCellFactory(new Callback<ListView<Anime>,ListCell<Anime>>() {

				@Override
				public ListCell<Anime> call(ListView<Anime> arg0) {
					ListCell<Anime> cell = new ListCell<Anime>() {
						@Override
						protected void updateItem(Anime anime, boolean btl) {
							super.updateItem(anime, btl);
							if (anime != null) {
								
								Image img = new Image(new ByteArrayInputStream(anime.getImagem()));
								ImageView imgview = new ImageView(img);;
								imgview.setFitHeight(80);
								imgview.setFitWidth(50);
								
								Button btnAdicionar = new Button("Mais informa��o");
								btnAdicionar.setOnAction(new EventHandler<ActionEvent>() {
								 @Override  //Metodo para abrir o openInfoAnime
						            public void handle(ActionEvent event) {  
						            	
						              	anime_id = anime.getAnime_id();
						            	WindowManager.openinfoAnime();
						            }
						        });
								
								 

								HBox hBox = new HBox(5);
			                    hBox.setAlignment(Pos.CENTER_LEFT); 	
			
			                    hBox.getChildren().addAll(
			                    		imgview,
			                            new Label(anime.getNome()),
			                    		new Label("",btnAdicionar));

			                    
			                    setGraphic(hBox);
								
							}
						}
					};
					return cell;
				}
				
			});
			listview.setItems(animeList);
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addAnimeVoltarVer(int user_id, int anime_id) {
		try {
			//Vai verificar se o utilizador ja tem esse anime adicionado no continuarver
			PreparedStatement statement = DBConnector.getConnection()
					.prepareStatement("select anime_id from continuarver where anime_id = ? and user_id = ?");
			statement.setInt(1, anime_id);
			statement.setInt(2, user_id);
			ResultSet results = statement.executeQuery();
			//Se o utilizador tiver esse anime ja adicionado no continuarver
			if (results.next()) {
				Dialog.warningDialog("", "Essa anime j� foi anteriormente adicionado ao continuar a ver", "Aten��o" );	
			}
			else {		
			//Vai verificar se o utilizador ja tem esse anime adicionado no voltarver
			PreparedStatement statement1 = DBConnector.getConnection()
					.prepareStatement("select anime_id from voltarver where anime_id = ? and user_id = ?");
			statement1.setInt(1, anime_id);
			statement1.setInt(2, user_id);
			ResultSet results1 = statement1.executeQuery();
			//Se o utilizador nao tiver esse anime ja adicionado no voltarver vai adiciona-lo
				if (!results1.next()) {
					
					//Vai fazer o insert do anime voltarver
					PreparedStatement statement2 = DBConnector.getConnection()
							.prepareStatement("INSERT INTO voltarver (anime_id, user_id) VALUES (?,?)");
					statement2.setInt(1, anime_id);
					statement2.setInt(2, user_id);
					statement2.executeUpdate();
					Dialog.warningDialog("", "Anime movido para o voltar a ver com sucesso!","Anime Movido!");
				}
				else{
    				Dialog.warningDialog("", "Esse anime j� existe no voltar a ver", "Aten��o" );	
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void addAnimeContinuarVer(int user_id, int anime_id) {
		try {
			//Vai verificar se o utilizador ja tem esse anime adicionado no voltarver
			PreparedStatement statement = DBConnector.getConnection()
					.prepareStatement("select anime_id from voltarver where anime_id = ? and user_id = ?");
			statement.setInt(1, anime_id);
			statement.setInt(2, user_id);
			ResultSet results = statement.executeQuery();
			//Se o utilizador tiver esse anime ja adicionado no voltarver
			if (results.next()) {
				Dialog.warningDialog("", "Essa anime j� foi anteriormente adicionado ao voltar a ver", "Aten��o" );	
			}
			else {	
			
			
			//Vai verificar se o utilizador ja tem esse anime adicionado no continuarver
			PreparedStatement statement1 = DBConnector.getConnection()
					.prepareStatement("select anime_id from continuarver where anime_id = ? and user_id = ?");
			statement1.setInt(1, anime_id);
			statement1.setInt(2, user_id);
			ResultSet results1 = statement1.executeQuery();
			//Se o utilizador nao tiver esse anime ja adicionado no continuarver vai adiciona-lo
				if (!results1.next()) {
					
					//Vai fazer o insert do anime continuarver
					PreparedStatement statement2 = DBConnector.getConnection()
							.prepareStatement("INSERT INTO continuarver (anime_id, user_id) VALUES (?,?)");
					statement2.setInt(1, anime_id);
					statement2.setInt(2, user_id);
					statement2.executeUpdate();
					Dialog.warningDialog("", "Anime movido para o continuar a ver com sucesso!","Anime Movido!");
				}
				else{
    				Dialog.warningDialog("", "Esse anime j� existe no continuar a ver", "Aten��o" );	
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	/**
	 *  Metedo que converter a imagem em binario
	 * @param is imagem 
	 * @return da imagem em binario
	 * @throws IOException imagem 
	 */
	public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
    	ByteArrayOutputStream os = new ByteArrayOutputStream(); 
    	byte[] buffer = new byte[0xFFFF];
    	for (int len = is.read(buffer); len != -1; len = is.read(buffer)) { 
    		os.write(buffer, 0, len);
    	}
    	return os.toByteArray();
    }
	
	/**
	 * Vai buscar a informa��o de um certo anime 
	 * @param animeID ID do anime
	 * @return ArrayList
	 */
	public static ArrayList<Anime> getInfoAnime(int animeID) {
		ArrayList<Anime> animeList = new ArrayList<Anime>();

		try {
			//%or% Finds any values that have "or" in any position
			PreparedStatement statement = DBConnector.getConnection().prepareStatement("select * from Animes WHERE anime_id= ?");
			statement.setInt(1, animeID );
			ResultSet results = statement.executeQuery();
			if(results.next()){
				int anime_id = results.getInt("anime_id");
				String nome= results.getString("nome");
				String link= results.getString("link");
				String data= results.getString("data");
				int episodes = results.getInt("episodes");
				float score = results.getFloat("score");
				int seasons = results.getInt("seasons");
				String autor= results.getString("autor");
				String synopsis= results.getString("synopsis");
				String genre = results.getString("genre");
                InputStream imagem = results.getBinaryStream("imagem");
                byte[] imagem1 = getBytesFromInputStream(imagem);
                
                animeList.add(new Anime(anime_id,nome,link,data,episodes,score,seasons,autor,synopsis,genre,imagem1));

			}
			
			
		
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return animeList;
		
	 }
	}
