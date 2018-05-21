/*Autor: Matheus Rotta Alves (184403).
 *Os comportamentos foram implementados como métodos dentro desta classe, e invocados da seguinte maneira:
 *Se a minha vida estiver entre 20 e 30, o método chamado é o controle;
 *Se estiver entre 11 e 19, o método chamado é o agressivo;
 *Enfim, de vida 10 até morrer, o método chamado é o curva de mana1.
 *Esta ordem foi escolhida por tentativa e erro, e foi escolhida por dar os melhores resultados contra o JogadorAleatorio.
 *(Embora eu saiba que talvez contra Jogadores não aleatórios esse critério possa não ser o mais adequado)
 *
 *MÉTODO AGRESSIVO
 *Baixa primeiro os Lacaios e depois as magias.
 *Se a magia for de buff escolhe um lacaio meu aleatoriamente para buffar. (só é utilizada se houver lacaios meus)
 *Se for de alvo, é direcionada ao herói.
 *Enfim, ataca o herói oponente com todos os meus lacaios.
 *
 *MÉTODO CONTROLE
 *Baixa Lacaios da mesma maneira que o agressivo.
 *A magia de área só é utilizada se houver dois ou mais lacaios oponentes na mesa
 *A magia de dano em alvo só é utilizada em lacaios e se for matar, e também desperdiça no máximo 1 de dano ao ser utilizada.
 *Os ataques com lacaios visam outros lacaios, sendo que são feitas apenas trocas favoráveis. Se não houver
 *trocas favoráveis, o lacaio ataca o herói oponente.
 *Obs: Antes de começar as trocas favoráveis, é testado se ao direcionar todos os ataques dos lacaios ao herói oponente ele morrerá, 
 *o que já ganha o jogo, nesse caso.
 *
 *MÉTODO CURVA DE MANA
 *Bem parecido com o método controle, mas busca a combinação de maior mana para baixar as cartas.
 *Se houver um lacaio cujo custo de mana seja igual a minhaMana, ele é priorizado.
 *Se não houver esse lacaio, buscará a maior combinação que não exceda minhaMana.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
* Esta classe representa o Jogador RA184403.
* @see java.lang.Object
* @author Matheus Rotta - MC302
*/
public class JogadorRA184403 extends Jogador {
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
	
	/**
	  * O método construtor do JogadorAleatorio.
	  * 
	  * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida. 
	  * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
	  */
	public JogadorRA184403(ArrayList<Carta> maoInicial, boolean primeiro){
		primeiroJogador = primeiro;
		
		mao = maoInicial;
		lacaios = new ArrayList<>();
		lacaiosOponente = new ArrayList<>();
		
		// Mensagens de depuração:
		//System.out.println("*Classe JogadorRAxxxxxx* Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
		//System.out.println("Mao inicial:");
		//for(int i = 0; i < mao.size(); i++)
			//System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}
	
	/**
	  * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
	  * 
	  * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
	  * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
	  * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
	  * @return            um ArrayList com as Jogadas decididas
	  */
	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
		int minhaMana;
		int minhaVida;
		int vidaOpon;
		if(cartaComprada != null)
			mao.add(cartaComprada);
		if(primeiroJogador){
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			vidaOpon = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
			//System.out.println("--------------------------------- Começo de turno pro jogador1");
		}
		else{
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			vidaOpon = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
			//System.out.println("--------------------------------- Começo de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		boolean criterioVida = false;
		//critérios para trocar entre um comportamento e outro:
		if(criterioVida) {
			if (minhaVida >= 20) minhasJogadas =  controle (minhasJogadas, minhaMana, lacaiosOponente, lacaios, vidaOpon);
			else if (minhaVida > 10 && minhaVida < 20) minhasJogadas = agressivo(minhasJogadas, minhaMana, lacaiosOponente);
			else minhasJogadas =  curvaDeMana1 (minhasJogadas, minhaMana, lacaiosOponente, lacaios, vidaOpon);
		}
		if (lacaiosOponente.size() > 0) minhasJogadas =  controle (minhasJogadas, minhaMana, lacaiosOponente, lacaios, vidaOpon);
		else if (minhaMana >= 9) minhasJogadas = curvaDeMana1 (minhasJogadas, minhaMana, lacaiosOponente, lacaios, vidaOpon);
		else minhasJogadas = agressivo(minhasJogadas, minhaMana, lacaiosOponente);
		
		
		return minhasJogadas;
	}


	private ArrayList<Jogada> agressivo (ArrayList<Jogada> minhasJogadas, int minhaMana, ArrayList<CartaLacaio> lacaiosOponente) {
		
		//baixar lacaios
		int lacaiosMesa = lacaios.size();
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaLacaio && card.getMana() <= minhaMana && lacaiosMesa < 7) {
				Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
				minhasJogadas.add(lac);
				lacaiosMesa++;
				minhaMana -= card.getMana();
				mao.remove(i);
				i--;
			}
		}
		
		//baixar magias
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				Jogada mag = null;
				if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() > 0){ //definindo o que acontece quando a carta eh de buff (talvez esse já fosse o comportamento, nao dah pra saber sem o codigo fonte do motor
					mag = new Jogada(TipoJogada.MAGIA, card, lacaios.get(ThreadLocalRandom.current().nextInt(0, lacaios.size()))); //esse metodo de random, para o valor maximo, eh exclusivo, portanto nao acessarei posicao invalida
				}
				else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA){ //essa magia causa dano, decrementar vida dos lacaios!
					mag = new Jogada(TipoJogada.MAGIA, card, null);
					for (CartaLacaio lac: lacaiosOponente) {
						lac.setVidaAtual(lac.getVidaAtual() - ((CartaMagia) card).getMagiaDano());
					}
				}
				else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO){
					mag = new Jogada(TipoJogada.MAGIA, card, null); 
				}
				else {
					continue;
				}
				minhasJogadas.add(mag);
				minhaMana -= card.getMana();
				mao.remove(i);
				i--;
			}
		}
		
		//atacar com lacaios
		for (int i = 0; i < lacaios.size(); i++) {
			Jogada atk = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
			minhasJogadas.add(atk); // ataca o heroi oponente com todos os lacaios que nao foram baixados neste turno
		}
		
		if (minhaMana >= 2) {
			Jogada pod = new Jogada(TipoJogada.PODER, null, null);
			minhasJogadas.add(pod);
			minhaMana -= 2;
		}
		
		return minhasJogadas;
	}
	
	
private ArrayList<Jogada> controle (ArrayList<Jogada> minhasJogadas, int minhaMana, ArrayList<CartaLacaio> lacaiosOponente, ArrayList<CartaLacaio> lacaios, int vidaOpon) {
		
		//baixar lacaios
		int lacaiosMesa = lacaios.size();
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaLacaio && card.getMana() <= minhaMana && lacaiosMesa < 7) {
				Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
				minhasJogadas.add(lac);
				lacaiosMesa++;
				minhaMana -= card.getMana();
				mao.remove(i);
				i--;
			}
		}
		//baixar magias
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				Jogada mag = null;
				if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() > 0){ //definindo o que acontece quando a carta eh de buff (talvez esse já fosse o comportamento, nao dah pra saber sem o codigo fonte do motor
					mag = new Jogada(TipoJogada.MAGIA, card, lacaios.get(ThreadLocalRandom.current().nextInt(0, lacaios.size()))); //esse metodo de random, para o valor maximo, eh exclusivo, portanto nao acessarei posicao invalida
				}
				else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA && lacaiosOponente.size() > 1){ //essa magia causa dano, decrementar vida dos lacaios!
					mag = new Jogada(TipoJogada.MAGIA, card, null);
					vidaOpon -= ((CartaMagia) card).getMagiaDano();
					for (int j = 0; j < lacaiosOponente.size(); j++) {
						CartaLacaio alvo = lacaiosOponente.get(j);
						alvo.setVidaAtual(alvo.getVidaAtual() - ((CartaMagia) card).getMagiaDano());
						if (alvo.getVidaAtual() < 1) {
							lacaiosOponente.remove(j); //remove o lacaio do oponente caso ele tenha morrido
							j--;
						}
					}
				}
				else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO){
//					mag = new Jogada(TipoJogada.MAGIA, card, null); 
//					vidaOpon -= ((CartaMagia) card).getMagiaDano();
					boolean found = false;
					for (int j = 0; j < lacaiosOponente.size(); j++) {
						//criterio pedido que deve-se usar a magia de alvo apenas em lacaios que morrerão e sem desperdiçar magias de alvo.
						if (((CartaMagia) card).getMagiaDano() - lacaiosOponente.get(j).getVidaAtual() == 1 || ((CartaMagia) card).getMagiaDano() - lacaiosOponente.get(j).getVidaAtual() == 0) {
							mag = new Jogada(TipoJogada.MAGIA, card, lacaiosOponente.get(j));
							lacaiosOponente.remove(j);
							found = true;
							break;
						}
						
					}
					if(!found) {
						continue;
					}
				}
				else {
					continue;
				}
				minhasJogadas.add(mag);
				minhaMana -= card.getMana();
				mao.remove(i);
				i--;
			}
		}
		
		//atacar com lacaios. Como estamos em controle, so ataca se a troca for FAVORAVEL
		/*há três tipos de trocas favoraveis (especificadas no enunciado) (x ataca y):
		 * caso1: x sobrevive ^ y morre;
		 * caso2: x morre ^ y morre ^ custoMana de y > custoMana de x;
		 * caso3: x morre ^ y morre ^ vidaAtual de x < vidaAtual de y.
		 */
		
		int sum = 0; //soma dos ataques
		for (int i = 0; i < lacaios.size(); i++) {
			sum += (lacaios.get(i)).getAtaque();
		}
		if (sum >= vidaOpon) {
			for (int i = 0; i < lacaios.size(); i++) {
				Jogada ataque = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
				minhasJogadas.add(ataque);
			}
		}
		else {
			//o ciclo a seguir executa a primeira troca favoravel que encontrar para o lacaio da iteracao
			for (int i = 0; i < lacaios.size(); i++) {
				Jogada atk = null;
				CartaLacaio atacante = lacaios.get(i);
				for (int j = 0; j < lacaiosOponente.size(); j++) {
					CartaLacaio alvo = lacaiosOponente.get(j);
					
					//caso 1:
					if (atacante.getVidaAtual() > alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque()) {
						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
						atacante.setVidaAtual(atacante.getVidaAtual() - alvo.getAtaque()); //mas o atacante sobrevive
						lacaiosOponente.remove(j); //pq temos certeza que alvo morreu.
						break;
					}
					//caso 2:
					else if (atacante.getVidaAtual() <= alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque() && alvo.getMana() > atacante.getMana()) {
						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
						lacaios.remove(i);
						i--;
						lacaiosOponente.remove(j);
						break;
					}
					//caso 3: 
					else if (atacante.getVidaAtual() <= alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque() && atacante.getVidaAtual() < alvo.getVidaAtual()) {
						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
						lacaios.remove(i);
						i--;
						lacaiosOponente.remove(j);
						break;
					}
				}
				if(atk == null) {
					atk = new Jogada(TipoJogada.ATAQUE, atacante, null);
				}
					minhasJogadas.add(atk); 
			}
		}
		
		
		
		if (minhaMana >= 2) {
			Jogada pod = new Jogada(TipoJogada.PODER, null, null);
			minhasJogadas.add(pod);
			minhaMana -= 2;
		}
		
		return minhasJogadas;
	}

	
	private ArrayList<Jogada> curvaDeMana1 (ArrayList<Jogada> minhasJogadas, int minhaMana, ArrayList<CartaLacaio> lacaiosOponente, ArrayList<CartaLacaio> lacaios, int vidaOpon) {
		
		/*
		 * Aqui eu busco a combinação de cartas da minha mão que usa a maior quantidade de mana possível sem exceder minhaMana
		 * Para tal, utilizei uma função que peguei do StackOverflow que me retorna todos os subarrays possíveis de um array passado como parâmetro.
		 * Aí, bastou percorrer todas as combinações e pegar a de maior mana. (que não estourasse minhaMana)
		 */
		
		boolean lacaioPica = false;
		for (int i = 0; i < mao.size(); i++) {
			if (mao.get(i) instanceof CartaLacaio && ((CartaLacaio) mao.get(i)).getMana() == minhaMana) {
				Jogada lac = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
				minhasJogadas.add(lac);
				minhaMana -= mao.get(i).getMana();
				mao.remove(i);
				lacaioPica = true;
				break;
			}
		}
		
		if (!lacaioPica) {
			List<Carta> best = null;
			int bestMana = 0;
			for (int k = 0; k < mao.size(); k++) {
				List<List<Carta>> combinations = combination(mao, k);
				for (int i = 0; i < combinations.size(); i++) {
					int totalMana = 0;
					for (int j = 0; j < (combinations.get(i)).size(); j++) {
							totalMana += ((combinations.get(i)).get(j)).getMana();
					}
					if (totalMana <= minhaMana && totalMana > bestMana) {
						best = combinations.get(i); //best entao será a combinacao de maior mana que nao excede minhaMana. 
					}
				}
			}
			int lacaiosMesa = lacaios.size();
			if (best != null) {
				for (int i = 0; i < best.size(); i++) {
					if (best.get(i) instanceof CartaLacaio && lacaiosMesa < 7) {
						Jogada lac = new Jogada(TipoJogada.LACAIO, best.get(i), null);
						minhasJogadas.add(lac);
						lacaiosMesa++;
						minhaMana -= (best.get(i)).getMana();
						mao.remove(best.get(i));
					}
					else if (best.get(i) instanceof CartaMagia) {
						Jogada mag = null;
						if (((CartaMagia) best.get(i)).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() > 0){ //definindo o que acontece quando a carta eh de buff (talvez esse já fosse o comportamento, nao dah pra saber sem o codigo fonte do motor
							mag = new Jogada(TipoJogada.MAGIA, best.get(i), lacaios.get(ThreadLocalRandom.current().nextInt(0, lacaios.size()))); //esse metodo de random, para o valor maximo, eh exclusivo, portanto nao acessarei posicao invalida
						}
						else if (((CartaMagia) best.get(i)).getMagiaTipo() == TipoMagia.AREA){ //essa magia causa dano, decrementar vida dos lacaios!
							mag = new Jogada(TipoJogada.MAGIA, best.get(i), null);
							vidaOpon -= ((CartaMagia) best.get(i)).getMagiaDano();
							for (int j = 0; j < lacaiosOponente.size(); j++) {
								CartaLacaio alvo = lacaiosOponente.get(j);
								alvo.setVidaAtual(alvo.getVidaAtual() - ((CartaMagia) best.get(i)).getMagiaDano());
								if (alvo.getVidaAtual() < 1) {
									lacaiosOponente.remove(j); //remove o lacaio do oponente caso ele tenha morrido
									j--;
								}
								
							}
						}
						else if (((CartaMagia) best.get(i)).getMagiaTipo() == TipoMagia.ALVO){
							mag = new Jogada(TipoJogada.MAGIA, best.get(i), null); 
							vidaOpon -= ((CartaMagia) best.get(i)).getMagiaDano();
						}
						else {
							continue;
						}
						minhasJogadas.add(mag);
						minhaMana -= (best.get(i)).getMana();
						mao.remove(best.get(i));
					}
				}
			}
		}
		
		
		
		//trocas foram copiadas do metodo controle!
		//atacar com lacaios. Como estamos em CurvaDeMana, so ataca se a troca for FAVORAVEL
		/*há três tipos de trocas favoraveis (especificadas no enunciado) (x ataca y):
		 * caso1: x sobrevive ^ y morre;
		 * caso2: x morre ^ y morre ^ custoMana de y > custoMana de x;
		 * caso3: x morre ^ y morre ^ vidaAtual de x < vidaAtual de y.
		 */
		
		int sum = 0; //soma dos ataques
		for (int i = 0; i < lacaios.size(); i++) {
			sum += (lacaios.get(i)).getAtaque();
		}
		if (sum >= vidaOpon) {
			for (int i = 0; i < lacaios.size(); i++) { //se a soma dos ataques dos meus lacaios for maior que a vida do heroi oponente, já dá pra ganhar o jogo.
				Jogada ataque = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
				minhasJogadas.add(ataque);
			}
		}
		else {
			//o ciclo a seguir executa a primeira troca favoravel que encontrar para o lacaio da iteracao
			for (int i = 0; i < lacaios.size(); i++) {
				Jogada atk = null;
				CartaLacaio atacante = lacaios.get(i);
				for (int j = 0; j < lacaiosOponente.size(); j++) {
					CartaLacaio alvo = lacaiosOponente.get(j);
					
					//caso 1:
					if (atacante.getVidaAtual() > alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque()) {
						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
						atacante.setVidaAtual(atacante.getVidaAtual() - alvo.getAtaque()); //mas o atacante sobrevive
						lacaiosOponente.remove(j); //pq temos certeza que alvo morreu.
						break;
					}
					else if (atacante.getVidaAtual() <= alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque() && alvo.getMana() > atacante.getMana()) {
						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
						lacaios.remove(i);
						i--;
						lacaiosOponente.remove(j);
						break;
					}
					else if (atacante.getVidaAtual() <= alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque() && atacante.getVidaAtual() < alvo.getVidaAtual()) {
						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
						lacaios.remove(i);
						i--;
						lacaiosOponente.remove(j);
						break;
					}
				}
				if(atk == null) {
					atk = new Jogada(TipoJogada.ATAQUE, atacante, null);
				}
					minhasJogadas.add(atk); 
			}
		}
		
		
		
		if (minhaMana >= 2) {
			Jogada pod = new Jogada(TipoJogada.PODER, null, null);
			minhasJogadas.add(pod);
			minhaMana -= 2;
		}
		
		return minhasJogadas;
	}
	/*
	 * Esse código a seguir não é de minha autoria, foi retirado de uma thread do stackoverflow e se passada uma lista, 
	 * ele retornará uma lista de listas com todos as sublistas possíveis. Isso foi utilizado em curvaDeMana1 para pegar
	 * a maior combinação de mana possível.
	 */
	private <T> List<List<T>> combination(List<T> values, int size) {

        if (0 == size) {
            return Collections.singletonList(Collections.<T> emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<List<T>>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));

        return combination;
    }
	
//	private boolean usarMagia (CartaMagia magia, ArrayList<CartaLacaio> lacaiosOponente) {
//		
//		if (magia.getMagiaTipo() == TipoMagia.BUFF) return false;
//		else if (magia.getMagiaTipo() == TipoMagia.AREA) {
//			int manaMortos = 0;
//			for (int i = 0; i < lacaiosOponente.size(); i++) {
//				if (magia.getMagiaDano() >= lacaiosOponente.get(i).getVidaAtual()) { //somem mais que o custo de mana da propria magia
//					manaMortos += lacaiosOponente.get(i).getMana();
//				}
//			}
//			if (magia.getMana() < manaMortos) return true;
//			else return false;
//		}
//		else if (magia.getMagiaTipo() == TipoMagia.ALVO) {
//			for (int i = 0; i < lacaiosOponente.size(); i++) {
//				if (magia.getMagiaDano() >= lacaiosOponente.get(i).getVidaAtual()) {
//					if (magia.getMana() < lacaiosOponente.get(i).getMana()) return true;
//				}
//			}
//			return false;
//		}
//		
//	}
	
//	private ArrayList<Jogada> curvaDeMana (ArrayList<Jogada> minhasJogadas, int minhaMana, ArrayList<CartaLacaio> lacaiosOponente, ArrayList<CartaLacaio> lacaios, int vidaOpon) {
//		
//		boolean lacaioPica = false;
//		for (int i = 0; i < mao.size(); i++) {
//			if (mao.get(i) instanceof CartaLacaio && ((CartaLacaio) mao.get(i)).getMana() == minhaMana) {
//				Jogada lac = new Jogada(TipoJogada.LACAIO, mao.get(i), null);
//				minhasJogadas.add(lac);
//				minhaMana -= mao.get(i).getMana();
//				mao.remove(i);
//				lacaioPica = true;
//			}
//		}
//		if (!lacaioPica) {
//			ArrayList<CartaLacaio> lacaiosMao = new ArrayList<CartaLacaio>();
//			ArrayList<CartaMagia> magiasMao = new ArrayList<CartaMagia>();
//			for (int i = 0; i < mao.size(); i++) {
//				if (mao.get(i) instanceof CartaLacaio) {
//					lacaiosMao.add((CartaLacaio) mao.get(i));
//				}
//				else if (mao.get(i) instanceof CartaMagia) {
//					magiasMao.add((CartaMagia) mao.get(i));
//				}
//			}
//			lacaiosMao.sort((o2, o1) -> ((Integer) o1.getMana()).compareTo((Integer)o2.getMana()));
//			magiasMao.sort((o2, o1) -> ((Integer) o1.getMana()).compareTo((Integer)o2.getMana()));
//			int bestMana = 0; 
//			int bestI = 0;
//			int bestJ = 0;
//			boolean found = false;
//			for(int i = 0; i < lacaiosMao.size(); i++) {
//				for (int j = 0; j < magiasMao.size(); j++) {
//					int totalMana = lacaiosMao.get(i).getMana() + magiasMao.get(j).getMana();
//					if (totalMana <= minhaMana && totalMana > bestMana) {
//						bestI = i;
//						bestJ = j;
//						bestMana = totalMana;
//						found = true;
//					}
//				}
//			}
//			if (found) {
//				Jogada lac = new Jogada(TipoJogada.LACAIO, lacaiosMao.get(bestI), null);
//				minhasJogadas.add(lac);
//				mao.remove(lacaiosMao.get(bestI));
//				minhaMana -= lacaiosMao.get(bestI).getMana();
//				Jogada mag = null;
//				if (magiasMao.get(bestJ).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() > 0){ //definindo o que acontece quando a carta eh de buff (talvez esse já fosse o comportamento, nao dah pra saber sem o codigo fonte do motor
//					mag = new Jogada(TipoJogada.MAGIA, magiasMao.get(bestJ), lacaios.get(ThreadLocalRandom.current().nextInt(0, lacaios.size()))); //esse metodo de random, para o valor maximo, eh exclusivo, portanto nao acessarei posicao invalida
//				}
//				else if (magiasMao.get(bestJ).getMagiaTipo() == TipoMagia.AREA){ //essa magia causa dano, decrementar vida dos lacaios!
//					mag = new Jogada(TipoJogada.MAGIA, magiasMao.get(bestJ), null);
//					vidaOpon -= magiasMao.get(bestJ).getMagiaDano();
//					for (int j = 0; j < lacaiosOponente.size(); j++) {
//						CartaLacaio alvo = lacaiosOponente.get(j);
//						alvo.setVidaAtual(alvo.getVidaAtual() - magiasMao.get(bestJ).getMagiaDano());
//						if (alvo.getVidaAtual() < 1) {
//							lacaiosOponente.remove(j); //remove o lacaio do oponente caso ele tenha morrido
//							j--;
//						}
//						
//					}
//				}
//				else if (magiasMao.get(bestJ).getMagiaTipo() == TipoMagia.ALVO){
//					mag = new Jogada(TipoJogada.MAGIA, magiasMao.get(bestJ), null); 
//					vidaOpon -= magiasMao.get(bestJ).getMagiaDano();
//				}
//				if (mag != null) {
//					minhasJogadas.add(mag);
//					minhaMana -= magiasMao.get(bestJ).getMana();
//					mao.remove(magiasMao.get(bestJ));
//				}
//				
//			}
//			
//		}
//		
//		
//
//		//atacar com lacaios. Como estamos em controle, so ataca se a troca for FAVORAVEL
//		/*há três tipos de trocas favoraveis (especificadas no enunciado) (x ataca y):
//		 * caso1: x sobrevive ^ y morre;
//		 * caso2: x morre ^ y morre ^ custoMana de y > custoMana de x;
//		 * caso3: x morre ^ y morre ^ vidaAtual de x < vidaAtual de y.
//		 */
//		//o ciclo a seguir executa a primeira troca favoravel que encontrar para o lacaio da iteracao
//		int sum = 0; //soma dos ataques
//		for (int i = 0; i < lacaios.size(); i++) {
//			sum += (lacaios.get(i)).getAtaque();
//		}
//		if (sum >= vidaOpon) {
//			for (int i = 0; i < lacaios.size(); i++) {
//				Jogada ataque = new Jogada(TipoJogada.ATAQUE, lacaios.get(i), null);
//				minhasJogadas.add(ataque);
//			}
//		}
//		else {
//			for (int i = 0; i < lacaios.size(); i++) {
//				Jogada atk = null;
//				CartaLacaio atacante = lacaios.get(i);
//				for (int j = 0; j < lacaiosOponente.size(); j++) {
//					CartaLacaio alvo = lacaiosOponente.get(j);
//					
//					//caso 1:
//					if (atacante.getVidaAtual() > alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque()) {
//						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
//						atacante.setVidaAtual(atacante.getVidaAtual() - alvo.getAtaque()); //mas o atacante sobrevive
//						lacaiosOponente.remove(j); //pq temos certeza que alvo morreu.
//						break;
//					}
//					else if (atacante.getVidaAtual() <= alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque() && alvo.getMana() > atacante.getMana()) {
//						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
//						lacaios.remove(i);
//						i--;
//						lacaiosOponente.remove(j);
//						break;
//					}
//					else if (atacante.getVidaAtual() <= alvo.getAtaque() && alvo.getVidaAtual() <= atacante.getAtaque() && atacante.getVidaAtual() < alvo.getVidaAtual()) {
//						atk = new Jogada(TipoJogada.ATAQUE, atacante, alvo);
//						lacaios.remove(i);
//						i--;
//						lacaiosOponente.remove(j);
//						break;
//					}
//				}
//				if(atk == null) {
//					atk = new Jogada(TipoJogada.ATAQUE, atacante, null);
//				}
//					minhasJogadas.add(atk); 
//			}
//		}
//		
//		
//		
//		if (minhaMana >= 2) {
//			Jogada pod = new Jogada(TipoJogada.PODER, null, null);
//			minhasJogadas.add(pod);
//			minhaMana -= 2;
//		}
//		
//		return minhasJogadas;
//	}
//
//
}
	














