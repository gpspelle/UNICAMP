/**
 * Descricao da estrategia utilizada:
 * 
 * As jogadas desse jogador sao baseadas em tres comportamentos: Agressivo, Controle e Curva de Mana
 * 
 * O comportamento agressivo tem como base atacar diretamente o heroi do oponente no menor tempo possivel, 
 * e abaixar lacaios fortes (por exemplo, com ataque maior que 3 nos primeiros turnos, e os de ataque 
 * mais forte, como o Mestre Mago e o Dragao a partir do quinto turno, quando ha mais mana para se gastar 
 * (o custo de mana do Mestre Mago eh 5). Tambem nesse metodo usa-se a magia de alvo no heroi do oponente e
 * a magia de buff para aumentar os danos no heroi. Por fim, se sobrar mana, usa-se o poder heroico.
 * 
 * O comportamento controle se baseia em controlar a situacao da mesa, nao deixando a mesa do oponente se 
 * fortalecer. Para isso, utiliza-se apenas magias de alvo se necessario (mais de 2 alvos) e magias de alvo
 * apenas se valer a pena (diferenca entre o dano da magia e a vida do alvo for no maximo 1). Quanto as trocas
 * favoraveis, verifica-se os ataques de cada lacaio, as vidas deles e compara tambem vida com ataque.
 * 
 * O comportamento curva de mana tem como base o equilibrio entre os dois outros metodos. Alem disso a mana
 * tem que ser utilizada do melhor jeito possivel. Primeiramente se baixam os lacaios com maior custo de mana,
 * se a minha mana permitir. Depois, usa-se as magias sempre pensando na razao (mana da minha magia)/(mana
 * do[s] meu[s] alvo[s]) ser menor que 1. Depois, se sobrar mana, utiliza-se o poder heroico. Para os ataques,
 * se houver muitos lacaios na mesa do oponente, faz-se as trocas favoraveis tais como o controle; sen nao,
 * ataca-se o heroi do oponente.
 * 
 * O comportamento agressivo eh usado quando a minha vida esta baixa (menor de 10), para que eu tente matar
 * o oponente antes que ele me mata; e tambem quando minha mana esta entre 3 e 7. que eh quando eu baixo os
 * lacaios no modo agressivo. O comportamento curva de mana eh usado no inicio do jogo, quando a mana eh baixa
 * (menor que 3), pois esse comportamento baixa lacaios de maneira mais inteligente que o agressivo; e tambem
 * quando a mana esta entre 7 e 10, pois seria desperdicio de mana usar toda ela em jogadas de numero maior
 * ou igual a 10, ja que a partir dai a mana nao recarrega. O comportamento controle eh usado quando ha muitos
 * lacaios na mesa do oponente, servindo para tirar o poder de fogo dele.
 * 
 */

import java.util.ArrayList;

public class JogadorRA174201 extends Jogador {
	
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
	
	public JogadorRA174201 (ArrayList<Carta> maoInicial, boolean primeiro) {
		
		primeiroJogador = primeiro;
		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();
		
		// Mensagens de depuracao:
		//System.out.println ("*Classe JogadorRA174201* Sou o " + (primeiro ? "primeiro" : "segundo") + " jogador (classe: JogadorRA174201)");
		//System.out.println ("Mao inicial:");
		//for (int i = 0; i < mao.size(); i++)
		//	System.out.println ("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}

	public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente) {
		
		int minhaMana, minhaVida;
		
		if (cartaComprada != null)
			mao.add (cartaComprada);
		
		if (primeiroJogador) {
			
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
			//System.out.println("--------------------------------- Comeco de turno pro jogador1");
		}
		
		else {
			
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
			//System.out.println("--------------------------------- Comeco de turno pro jogador2");
		}
		
		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		
		//Chamada dos comportamentos
		
		if (lacaiosOponente.size() > 5)
			minhasJogadas = controle (minhaMana, minhasJogadas);
		
		else {
		
			if (minhaVida <= 10 || (minhaMana > 3 && minhaMana <= 7))
				minhasJogadas = agressivo (minhaMana, minhasJogadas);
		
			if (minhaMana <= 3 || (minhaMana > 7 && minhaMana < 10))
				minhasJogadas = curvaDeMana (minhaMana, minhasJogadas);
		}
		
		
		return minhasJogadas;
	}
	
	// MODO AGRESSIVO ********************************************
		
	private ArrayList<Jogada> agressivo (int minhaMana, ArrayList<Jogada> minhasJogadas) {
		
		//Baixa lacaios de ataque mais forte
		int i, j, k;
		CartaLacaio cardMax = new CartaLacaio (0,null,0,0,0,0,null,0);
		k = 0;
		
		for (j = 0; j < mao.size(); j++) {
			
			for (i = 0; i < mao.size(); i++) {
				
				Carta card = mao.get(i);
				if (card instanceof CartaLacaio)
					if (((CartaLacaio)card).getAtaque() >= cardMax.getAtaque()) {
						
						cardMax = (CartaLacaio)card;
						k = i;
					}
			}
			
			if (cardMax.getMana() <= minhaMana && minhaMana >= 5) {
				
				Jogada lac = new Jogada (TipoJogada.LACAIO, cardMax, null);
				minhasJogadas.add (lac);
				minhaMana -= cardMax.getMana();
				mao.remove(k);
				i--;
			}
			
			else { 
				
				Carta card = mao.get(j);
				if (card instanceof CartaLacaio && ((CartaLacaio)card).getAtaque() >= 3 && card.getMana() <= minhaMana) {
				
					Jogada lac = new Jogada (TipoJogada.LACAIO, card, null);
					minhasJogadas.add (lac);
					minhaMana -= card.getMana();
					mao.remove(j);
					j--;
				}
			}
		}
		
		//Magias de alvo no heroi
		for (j = 0; j < mao.size(); j++) {
			
			Carta card = mao.get(j);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				
				if (((CartaMagia)card).getMagiaTipo() == TipoMagia.ALVO) {
					
					Jogada mag = new Jogada (TipoJogada.MAGIA, card, null);
					minhasJogadas.add (mag);
					minhaMana -= card.getMana();
					//System.out.println("Jogada: Decidi uma jogada de usar a magia: " + card);
					mao.remove(j);
				}
			}
		}
		
		//Magias de buff
		for (i = 0; i < mao.size(); i++) {
			
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				
				Jogada mag = null;
				
				if (((CartaMagia)card).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() > 0) {
					
					mag = new Jogada (TipoJogada.MAGIA, card, lacaios.get(0));
					minhasJogadas.add (mag);
					minhaMana -= card.getMana();
					mao.remove(i);
				}
			}
		}
		
		for (i = 0; i < lacaios.size(); i++) {
			
			Carta card = lacaios.get(i);
			Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, null);
			minhasJogadas.add (ataque);
		}
		
		if (minhaMana >= 2) {
			
			Jogada poder = new Jogada (TipoJogada.PODER, null, null);
			minhasJogadas.add (poder);
			minhaMana -= 2;
		}
		
		return minhasJogadas;
		
	}
	
	// MODO CONTROLE ***************************************************
	
	private ArrayList<Jogada> controle (int minhaMana, ArrayList<Jogada> minhasJogadas) {
		
		//Uso de magias controlado
		for (int i = 0; i < mao.size(); i++) {
			
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				
				Jogada mag = null;
				
				if (((CartaMagia)card).getMagiaTipo() == TipoMagia.AREA) {
					
					if (lacaiosOponente.size() >= 2) {
						
						mag = new Jogada (TipoJogada.MAGIA, card, null);
						minhasJogadas.add(mag);
						minhaMana -= card.getMana();
						mao.remove(i);
					}
				}
				
				else if (((CartaMagia)card).getMagiaTipo() == TipoMagia.ALVO && lacaiosOponente.size() > 0) {
					
					int j = 0;
					while (Math.abs(((CartaMagia)card).getMagiaDano() - lacaiosOponente.get(j).getVidaAtual()) > 1)
						j++;
					
					if (lacaiosOponente.get(j) != null) {
						mag = new Jogada (TipoJogada.MAGIA, card, lacaiosOponente.get(j));
						minhasJogadas.add(mag);
						minhaMana -= card.getMana();
						mao.remove(i);
					}
				}
				
				else if (((CartaMagia)card).getMagiaTipo() == TipoMagia.BUFF && lacaios.size() > 0) {
					
					mag = new Jogada (TipoJogada.MAGIA, card, lacaios.get(0));
					minhasJogadas.add (mag);
					minhaMana -= card.getMana();
					//System.out.println("Jogada: Decidi uma jogada de usar a magia: " + card);
					mao.remove(i);
				}
			}
		}
		
		//Ataque com trocas favoraveis
		for (int i = 0; i < lacaios.size(); i++) {
			
			Carta card = lacaios.get(i);
			for (int j = 0; j < lacaiosOponente.size(); j++) {
				
				if (lacaiosOponente.get(j).getVidaAtual() <= 0) {
					
					lacaiosOponente.remove(j);
					j++;
				}
				
				if (((CartaLacaio)card).getAtaque() > lacaiosOponente.get(j).getAtaque()) {
					
					if (((CartaLacaio)card).getVidaAtual() > lacaiosOponente.get(j).getAtaque()) {
						Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, lacaiosOponente.get(j));
						minhasJogadas.add (ataque);
						break;
					}
				}
				
				else {
					
					if (((CartaLacaio)card).getVidaAtual() > lacaiosOponente.get(j).getAtaque()) {
						
						Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, lacaiosOponente.get(j));
						minhasJogadas.add (ataque);
						break;
					}
					
					else {
						
						if (((CartaLacaio)card).getAtaque() >= lacaiosOponente.get(j).getVidaAtual() && ((CartaLacaio)card).getMana() < lacaiosOponente.get(j).getMana()) {
							
							Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, lacaiosOponente.get(j));
							minhasJogadas.add (ataque);
							break;
						}
						
						else {
							
							Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, null);
							minhasJogadas.add (ataque);
							break;
						}
					}
				}
			}
		}
		
		return minhasJogadas;
	}
		
	// MODO CURVA DE MANA ************************************************
	
	private ArrayList<Jogada> curvaDeMana (int minhaMana, ArrayList<Jogada> minhasJogadas) {
		
		//Baixa prioritariamente lacaios com maior custo de mana
		int minhaManaCopia = minhaMana;
		for (int j = 0; j < mao.size(); j++) {
			
			for (int i = 0; i < mao.size(); i++) {
				
				Carta card = mao.get(i);
				if (card instanceof CartaLacaio && card.getMana() == minhaManaCopia) {
					
					Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
					minhasJogadas.add (lac);
					minhaMana -= card.getMana();
					mao.remove(i);
					minhaManaCopia = minhaMana;
				}
			}
			
			minhaManaCopia--;
		}
		
		//Usa magias
		for (int i = 0; i < mao.size(); i++) {
			
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				
				Jogada mag = null;
				
				//Usa magia de area se a soma das manas dos alvos for maior que o custo de mana da magia
				if (((CartaMagia)card).getMagiaTipo() == TipoMagia.AREA && lacaiosOponente.size() > 0) {
					
					int manaSomaOp = 0;
					for (int k = 0; k < lacaiosOponente.size(); k++)
						manaSomaOp += lacaiosOponente.get(k).getMana();
					
					if (((CartaMagia)card).getMana() < manaSomaOp) {
					
						mag = new Jogada (TipoJogada.MAGIA, card, null);
						minhasJogadas.add (mag);
						minhaMana -= card.getMana();
						mao.remove(i);
					}
				}
				
				int k;
				if (((CartaMagia)card).getMagiaTipo() == TipoMagia.ALVO && lacaiosOponente.size() > 0) {
					
					int j = 0;
					for (k = 0; k < lacaiosOponente.size(); k++) {
						
						if (((CartaMagia)card).getMana() < lacaiosOponente.get(k).getMana())
							break;
						else
							j++;
					}
					
					if (j < lacaiosOponente.size() && lacaiosOponente.get(k).getVidaAtual() > 0) {
						
						mag = new Jogada (TipoJogada.MAGIA, card, lacaiosOponente.get(k));
						minhasJogadas.add (mag);
						minhaMana -= card.getMana();
						mao.remove(i);
					}
					
					else {
						
						mag = new Jogada (TipoJogada.MAGIA, card, null);
						minhasJogadas.add (mag);
						minhaMana -= card.getMana();
						mao.remove(i);
					}
				}
			}
		}
		
		// Poder heroico
		if (minhaMana >= 2) {
					
			Jogada poder = new Jogada (TipoJogada.PODER, null, null);
			minhasJogadas.add (poder);
			minhaMana -= 2;
		}
		
		//Ataques
		
		if (lacaiosOponente.size() > 3) {
		
			for (int i = 0; i < lacaios.size(); i++) {
			
				Carta card = lacaios.get(i);
				for (int j = 0; j < lacaiosOponente.size(); j++) {
					
					if (lacaiosOponente.get(j).getVidaAtual() <= 0) {
						
						lacaiosOponente.remove(j);
						j++;
					}
					
					if (((CartaLacaio)card).getAtaque() > lacaiosOponente.get(j).getAtaque()) {
						
						if (((CartaLacaio)card).getVidaAtual() > lacaiosOponente.get(j).getAtaque()) {
							Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, lacaiosOponente.get(j));
							minhasJogadas.add (ataque);
							break;
						}
					}
					
					else {
						
						if (((CartaLacaio)card).getVidaAtual() > lacaiosOponente.get(j).getAtaque()) {
							
							Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, lacaiosOponente.get(j));
							minhasJogadas.add (ataque);
							break;
						}
						
						else {
							
							if (((CartaLacaio)card).getAtaque() >= lacaiosOponente.get(j).getVidaAtual() && ((CartaLacaio)card).getMana() < lacaiosOponente.get(j).getMana()) {
								
								Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, lacaiosOponente.get(j));
								minhasJogadas.add (ataque);
								break;
							}
							
							else {
								
								Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, null);
								minhasJogadas.add (ataque);
								break;
							}
						}
					}
				}
			}
		}
		
		else {
			
			for (int i = 0; i < lacaios.size(); i++) {
				
				Carta card = lacaios.get(i);
				Jogada ataque = new Jogada (TipoJogada.ATAQUE, card, null);
				minhasJogadas.add (ataque);
			}
		}
		
		return minhasJogadas;
	}
}
