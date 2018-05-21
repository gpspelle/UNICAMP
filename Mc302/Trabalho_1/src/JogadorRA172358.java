/* Estrategia do Jogador
 *
 * A estrategia de jogo baseia-se em tres comportamentos: 1. Agressivo; 2. Controle e 3. Curva de Mana.
 *
 * 1. O objetivo do comportamento agressivo eh retirar o maximo de vida possivel do heroi inimigo, colocando na mesa os
 *  lacaios mais fortes, utilizando com prioridade as magias de area e alvo e nao realizando trocas com o oponente, ja que
 *  as politicas de mudanca de comportamento visam que o comportamento agressivo mate o adversario; e caso haja mana, o
 *  poder heroico eh utilizado utilizar o poder heroico.
 *
 * 2. O comportamento controle visa a diminuir o dano tomado pelo meu heroi, destruindo os lacaios do oponente, seja com
 * magias de area e alvo ou atacando os lacaios inimigos e realizando trocas favoraveis, caso haja mana ao fim do turno,
 * utiliza-se o poder heroico.
 *
 * Os criterios utilizados para realizar uma trocas sao
 *
 * - se o ataque do inimigo for menor que a minha vida e o meu ataque for maior que a vida do inimigo, a troca eh realizada.
 * - se o ataque do inimigo for maior ou igual a minha vida e o meu ataque for menor ou igual a vida do inimigo, a troca
 * eh realizada
 * - se o ataque do inimigo for maior ou igual a minha vida e o meu ataque for menor ou igual a minha vida e a minha vida
 * for menor que a vida do inimigo a troca eh realizada
 * - do contrario, o heroi inimigo eh atacado
 *
 * Os criterios para utilizar as cartas de magia sao:
 *
 * - caso eu possua uma magia de alvo e consiga matar o inimigo com ela, utilize-a no heroi inimigo
 * - caso eu tenha uma magia de alvo e tenha um lacaio inimigo com o modulo da sua vida menos o dano da carta tenha valor
 * menor ou igual a 1, utilize-a nesse lacaio
 * - do contrario, o heroi inimigo eh atacado
 *
 * 3. No modo curva de mana, a intencao eh gastar toda a mana com o uso de uma combinacao de ate tres cartas uma carta do
 * tipo Lacaio e/ou Magia e tambem realizar
 * trocas que diminuam o valor de mana das cartas inimigas, ou seja, trocar apenas quando o custo de mana inimigo for
 * maior do que o meu custo de mana para aquele lacaio.
 *
 * Os criterios utilizados para atacar com os lacaios  foram o seguinte:
 *
 * - so serao realizadas trocas se o dano total dos meus lacaios for menor do que do que a vida total do oponente, caso
 * seja contrario, o heroi inimigo sera atacado com todos os lacaios.
 * - caso o ataque do meu lacaio seja maior ou igual a vida do lacaio inimigo e mana do meu lacaio seja menor do que a
 * mana do inimigo, e o meu lacaio saia vivo da troca, a troca eh realizada
 *
 * Para alterar o comportamento, foram utilizados os seguintes parametros: o dano que pode ser causado pelos lacaios
 * inimigos que estao na mesa (danoVivoNaMesa), a vida do heroi inimigo (vidaInimigo), o dano que pode ser causado no
 * heroi inimigo pelas minhas cartas (meuDano) e tambem o fato de possuirmos na mao uma sequencia de cartas que levem a
 * gastar toda a mana do jogaddor.
 *
 * A sequencia de importancia para a escolha dos eh a seguinte:
 *
 * Sempre saber se eu possuo uma jogada que acaba com a minha mana. Caso o inimigo possa me matar, modo controle. Caso
 * eu mate o inimigo, modo agressivo. Do contrário, uso o modo de mana.
 *
 */
import java.util.ArrayList;
import java.util.Iterator;
/**
* Esta classe representa um Jogador aleatório (realiza jogadas de maneira aleatória) para o jogo LaMa (Lacaios & Magias).
* @see Object
* @author MO322
*/
public class JogadorRA172358 extends Jogador {
    private int minhaMana;
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;
    private int minhaVida;
    /**
     * O método construtor do JogadorAleatorio.
     *
     * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida.
     * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
     * _return um objeto JogadorAleatorio
     */
    public JogadorRA172358(ArrayList<Carta> maoInicial, boolean primeiro) {
        primeiroJogador = primeiro;
        mao = maoInicial;
        lacaios = new ArrayList<>();
        lacaiosOponente = new ArrayList<>();
    }

    private void modoAgressivo(ArrayList<Jogada> minhasJogadas, int vidaInimigo, int meuDano) {
        /*Percorre a mao para usar cartas de alvo ou area que possibilitem matar o inimigo*/
        int manaAux = minhaMana;
        int danoDeMagias = 0;
        ArrayList<CartaMagia> cartasMagias = new ArrayList<>();
        for(int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if(card instanceof CartaMagia && card.getMana() <= manaAux && ((CartaMagia) card).getMagiaTipo() != TipoMagia.BUFF) {
                danoDeMagias += ((CartaMagia) card).getMagiaDano();
                cartasMagias.add((CartaMagia) card);
                manaAux -= card.getMana();
            }
        }
        if(danoDeMagias + meuDano >= vidaInimigo) {
            for(CartaMagia i: cartasMagias) {
                adicionaCartaMagia(minhasJogadas, i, null);
                if(i.getMagiaTipo() == TipoMagia.AREA) {
                    for(CartaLacaio k : lacaiosOponente)  {
                        k.setVidaAtual(k.getVidaAtual() - i.getMagiaDano());
                    }
                }
            }
        }
        /*Esse for pega as magias da mao e usa no heroi inimigo*/
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA) {
                    vidaInimigo -= ((CartaMagia) card).getMagiaDano();
                    adicionaCartaMagia(minhasJogadas, card, null);
                    for(CartaLacaio k : lacaiosOponente) {
                        k.setVidaAtual(k.getVidaAtual() - ((CartaMagia) card).getMagiaDano());
                    }
                    i--;
                } else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO &&
                        ((CartaMagia) card).getMagiaDano() >= vidaInimigo) {
                    vidaInimigo -= ((CartaMagia) card).getMagiaDano();
                    adicionaCartaMagia(minhasJogadas, card, null);
                    i--;
                } else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF) {
                    if (lacaios.size() > 0) {
                        adicionaCartaMagia(minhasJogadas, card, lacaios.get(lacaios.size() - 1));
                        vidaInimigo -= ((CartaMagia) card).getMagiaDano();
                        i--;
                    }
                }
            }
        }

         /*Organiza as cartas da mao de acordo com ataque do lacaio, sem deslocar as cartas do tipo magia*/
        mao.sort((o1, o2) -> {
            if (o2 instanceof CartaLacaio && o1 instanceof CartaLacaio) {
                return ((CartaLacaio) o2).getAtaque() - ((CartaLacaio) o1).getAtaque();
            } else
                return o1.getMana() - o2.getMana();
        });

        /*Colocar na mesa os lacaios mais fortes primeiro*/
        baixaLacaios(mao, minhasJogadas);


        /*Atacar o heroi inimigo com os lacaios da mesa*/
        vidaInimigo -= atacarComLacaios(minhasJogadas, lacaios);

        /*Usar o poder heroico ao final da rodada se houver mana para atacar o heroi inimigo*/
        poderHeroico(minhasJogadas, vidaInimigo);
    }

    private void modoControle(ArrayList<Jogada> minhasJogadas, int vidaInimigo, int meuDano) {
        /*Pegar as cartas de magia da mao e utilizar, seguindo algumas regras descritas no cabecalho*/
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && minhaMana >= card.getMana()) {
                if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA) {
                    vidaInimigo -= ((CartaMagia) card).getMagiaDano();
                    adicionaCartaMagia(minhasJogadas, card, null);
                    /*Diminui a vida dos lacaios inimigos*/
                    for (CartaLacaio x : lacaiosOponente) {
                        x.setVidaAtual(x.getVidaAtual() - ((CartaMagia) card).getMagiaDano());
                    }
                    i--;
                }
                /*Caso eu nao consiga matar o adversario com a magia de alvo, utilizo-a no heroi inimigo*/
                else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO &&
                        ((CartaMagia) card).getMagiaDano() >= vidaInimigo) {
                    adicionaCartaMagia(minhasJogadas, card, null);
                    vidaInimigo -= ((CartaMagia) card).getMagiaDano();
                    i--;
                }
                /*Caso contrario, tentarei fazer uma troca favoravel com os lacaios inimigos*/
                else if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO) {
                    CartaLacaio alvo = null;
                    int index = -1;
                    for (int k = 0; k < lacaiosOponente.size(); k++) {
                        CartaLacaio inimigo = lacaiosOponente.get(k);
                        if ((inimigo.getVidaAtual() == ((CartaMagia) card).getMagiaDano()
                                || inimigo.getVidaAtual() + 1 == ((CartaMagia) card).getMagiaDano()
                                || inimigo.getVidaAtual() - 1 == ((CartaMagia) card).getMagiaDano()
                        )) {
                            alvo = inimigo;
                            index = k;
                        }
                    }
                    adicionaCartaMagia(minhasJogadas, card, alvo);
                    if (index != -1) {
                        lacaiosOponente.get(index).setVidaAtual(lacaiosOponente.get(index).getVidaAtual()
                                - ((CartaMagia) card).getMagiaDano());
                    } else {
                        vidaInimigo -= ((CartaMagia) card).getMagiaDano();
                    }
                    i--;
                }
            }
        }

        baixaLacaios(mao, minhasJogadas);

        /*Atacar os lacaios inimigos com os lacaios da mesa para tentar destruir as tropas inimigas e minimizar os danos recebidos por ataques*/
        for (CartaLacaio i : lacaios) {
            /*target guarda um possivel lacaio inimigo para ser atacado*/
            CartaLacaio target = null;
            int index = 0;
            /*found diz se eu encontrei um lacaio inimigo para atacar*/
            boolean found = false;
            if(meuDano < vidaInimigo) {
                for (int k = 0; k < lacaiosOponente.size() && !found; k++) {
                    /*aux representa um lacaio inimigo*/
                    CartaLacaio aux = lacaiosOponente.get(k);
                    found = false;
                    if (aux.getVidaAtual() > 0) {
                        if (aux.getAtaque() < i.getVidaAtual() && i.getAtaque() > aux.getVidaAtual()) {
                            found = true;
                        } else if (aux.getAtaque() >= i.getVidaAtual() && i.getAtaque() == aux.getVidaAtual()
                                && aux.getMana() > i.getMana()) {
                            found = true;
                        } else if (aux.getAtaque() >= i.getVidaAtual() && i.getAtaque() <= aux.getVidaAtual()
                                && aux.getVidaAtual() < i.getVidaAtual()) {
                            found = true;
                        }
                        if (found) {
                            index = k;
                            target = aux;
                        }
                    }
                }
            }
            if (target != null) {
                lacaiosOponente.get(index).setVidaAtual(lacaiosOponente.get(index).getVidaAtual() - i.getAtaque());
            } else {
               vidaInimigo -= i.getAtaque();
            }
            Jogada atk = new Jogada(TipoJogada.ATAQUE, i, target);
            minhasJogadas.add(atk);
            meuDano += i.getAtaque();
        }

        /*Uso do poder heroico, caso possivel*/
        poderHeroico(minhasJogadas, vidaInimigo);

    }

    private void modoMana(ArrayList<Jogada> minhasJogadas, ArrayList<Carta> jogadasMana, int vidaInimigo, int meuDano) {
        Iterator<Carta> iter = jogadasMana.iterator();
        Iterator<Carta> iter_2 = jogadasMana.iterator();
        /*iter e iter_2 percorrem o ArrayList de jogadasMana e permitem a exclusao das jogadas ja realizadas*/
        /*O while abaixo percorre as jogadas de mana em busca de uma jogada de area*/
        while(iter.hasNext()) {
            Carta c = iter.next();
            if(c instanceof CartaMagia && ((CartaMagia) c).getMagiaTipo() == TipoMagia.AREA) {
               Jogada mag = new Jogada(TipoJogada.MAGIA, c, null);
               minhasJogadas.add(mag);
               for(CartaLacaio j: lacaiosOponente) {
                    j.setVidaAtual(j.getVidaAtual() - ((CartaMagia)c).getMagiaDano());
               }
               minhaMana -= c.getMana();
               mao.remove(c);
               vidaInimigo -= ((CartaMagia) c).getMagiaDano();
            }
        }
        int numLacaios = lacaios.size();
        /*O while abaixo busca jogadas de carta lacaio e magia em jogadasMana, seguindo os padroes de regras descritos no
        * cabecalho*/
        while(iter_2.hasNext()) {
            Carta k = iter_2.next();
            if(k instanceof CartaLacaio && numLacaios < 7) {
                Jogada lac = new Jogada(TipoJogada.LACAIO, k, null);
                minhasJogadas.add(lac);
                numLacaios++;
                minhaMana -= k.getMana();
                mao.remove(k);
            } else if (k instanceof CartaMagia){
                if(((CartaMagia) k).getMagiaTipo() == TipoMagia.ALVO) {
                    if(meuDano >= vidaInimigo) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, k, null);
                        minhasJogadas.add(mag);
                        minhaMana -= k.getMana();
                        mao.remove(k);
                    } else {
                        int index = -1;
                        CartaLacaio alvo = null;
                        for (int x = 0; x < lacaiosOponente.size(); x++) {
                            CartaLacaio inimigo = lacaiosOponente.get(x);
                            if (inimigo.getVidaAtual() > 0 && (inimigo.getVidaAtual() <= ((CartaMagia) k).getMagiaDano()) && inimigo.getMana() >= k.getMana()) {
                                alvo = inimigo;
                                index = x;
                            }
                        }
                        adicionaCartaMagia(minhasJogadas, k, alvo);
                        if (index != -1) {
                            lacaiosOponente.get(index).setVidaAtual(lacaiosOponente.get(index).getVidaAtual()
                                - ((CartaMagia) k).getMagiaDano());
                        } else {
                            vidaInimigo -= ((CartaMagia) k).getMagiaDano();
                        }
                        mao.remove(k);
                        minhaMana -= k.getMana();
                        vidaInimigo -= ((CartaMagia) k).getMagiaDano();
                    }
                } else if(((CartaMagia) k).getMagiaTipo() == TipoMagia.BUFF) {
                    /*Buffar o ultimo lacaio da mesa*/
                    if(lacaios.size() > 0) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, k, lacaios.get(lacaios.size() - 1));
                        minhasJogadas.add(mag);
                        minhaMana -= k.getMana();
                        mao.remove(k);
                    }
                }
            }
        }
        /*Percorre os lacaios da mesa para atacar o heroi inimigo ou fazer trocas*/
        for (CartaLacaio i : lacaios) {
            if(meuDano < vidaInimigo || (meuDano + 1 == vidaInimigo && minhaMana == 2)) {
                CartaLacaio target = null;
                boolean found = false;
                int index = 0;
                for (int k = 0; k < lacaiosOponente.size() && !found; k++) {
                    CartaLacaio alvo = lacaiosOponente.get(k);
                    if (i.getAtaque() >= alvo.getVidaAtual() && i.getMana() < alvo.getMana()
                            && alvo.getVidaAtual() > 0
                            && i.getVidaAtual() > alvo.getAtaque()) {
                        found = true;
                        index = k;
                        target = alvo;
                    }
                }
                Jogada lac = new Jogada(TipoJogada.ATAQUE, i, target);
                minhasJogadas.add(lac);
                if (target != null) {
                    lacaiosOponente.get(index).setVidaAtual(lacaiosOponente.get(index).getVidaAtual() - i.getAtaque());
                } else {
                    vidaInimigo -= i.getAtaque();
                }
            }
            /*Caso consiga matar o heroi combinando meus ataques com o poder heroico*/
            else {
                Jogada lac = new Jogada(TipoJogada.ATAQUE, i, null);
                minhasJogadas.add(lac);
                vidaInimigo -= i.getAtaque();
            }
        }

        poderHeroico(minhasJogadas, vidaInimigo);
    }

    /*Recebe uma carta de magia e ataca o alvo com ela*/
    private void adicionaCartaMagia(ArrayList<Jogada> minhasJogadas, Carta card, Carta alvo) {
        Jogada mag = new Jogada(TipoJogada.MAGIA, card, alvo);
        minhasJogadas.add(mag);
        mao.remove(card);
        minhaMana -= card.getMana();
    }

    /*Recebe uma carta lacaio e adiciona-a a mesa*/
    private void adicionaCartaLacaio(ArrayList<Jogada> minhasJogadas, int index, Carta card) {
        Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
        minhasJogadas.add(lac);
        mao.remove(index);
        minhaMana -= card.getMana();
    }

    /*Percorre a mao e baixa os lacaios que a mana permite*/
    private void baixaLacaios(ArrayList<Carta> mao, ArrayList<Jogada> minhasJogadas) {
        int quantidadeLacaios = lacaios.size();
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if(quantidadeLacaios < 7) {
                if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
                    quantidadeLacaios++;
                    adicionaCartaLacaio(minhasJogadas, i, card);
                    i--;
                }
            }
        }
    }

    /*Percorre os lacaios da mesa para atacar o heroi*/
    private int atacarComLacaios(ArrayList<Jogada> minhasJogadas, ArrayList<CartaLacaio> lacaios) {
        int aux = 0;
        for (CartaLacaio i : lacaios) {
            Jogada lac = new Jogada(TipoJogada.ATAQUE, i, null);
            minhasJogadas.add(lac);
            aux += i.getAtaque();
        }
        return aux;
    }

    /*Utiliza o poder heroico no heroi inimigo ou em um lacaio com 1 de vida*/
    private int poderHeroico(ArrayList<Jogada> minhasJogadas, int vidaInimigo) {
        if (minhaMana >= 2) {
            CartaLacaio alvo = null;
            for (CartaLacaio k : lacaiosOponente) {
                if (k.getVidaAtual() == 1) {
                    alvo = k;
                }
            }
            if(vidaInimigo == 1) {
                Jogada lac = new Jogada (TipoJogada.PODER, null, null);
                minhasJogadas.add(lac);
                return 1;
            } else {
                Jogada lac = new Jogada(TipoJogada.PODER, null, alvo);
                minhasJogadas.add(lac);
                return 0;
                }
        }
        return 0;
    }

    /*Retorna a somatoria de ataques de um arraylist de cartas de lacaio*/
    private int getAtaque(ArrayList<CartaLacaio> cartas) {
            int aux = 0;
            for(CartaLacaio i: cartas) {
                aux += i.getAtaque();
            }
            return aux;
    }

    /**
     * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
     *
     * @param mesa            O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
     * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
     * @param jogadasOponente Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
     * @return um ArrayList com as Jogadas decididas
     */
    public ArrayList<Jogada> processarTurno(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente) {

        int danoVivoNaMesa;
        int meuDano;

        if (cartaComprada != null)
            mao.add(cartaComprada);
        if (primeiroJogador) {
            minhaMana = mesa.getManaJog1();
            minhaVida = mesa.getVidaHeroi1();
            lacaios = mesa.getLacaiosJog1();
            lacaiosOponente = mesa.getLacaiosJog2();
        } else {
            minhaMana = mesa.getManaJog2();
            minhaVida = mesa.getVidaHeroi2();
            lacaios = mesa.getLacaiosJog2();
            lacaiosOponente = mesa.getLacaiosJog1();
        }

        ArrayList<Jogada> minhasJogadas = new ArrayList<>();

        int vidaInimigo;

        if (primeiroJogador) {
            vidaInimigo = mesa.getVidaHeroi2();
        } else {
            vidaInimigo = mesa.getVidaHeroi1();
        }

        /*Ordenando as cartas do baralho de acordo com o gasto de mana, para sempre possibilitar que a primeira magia
        * a ser utilizada, caso possivel, seja a de Area*/
        mao.sort((o1, o2) -> {
                    if (o1 instanceof CartaLacaio && o2 instanceof CartaLacaio) {
                        return ((CartaLacaio) o2).getAtaque() - ((CartaLacaio) o1).getAtaque();
                    } else {
                        return o1.getMana() - o2.getMana();
                    }
                }
        );

        /*Descobrindo o poder de ataque dos lacaios inimigos que estao na mesa*/
        danoVivoNaMesa = getAtaque(lacaiosOponente);

        /*Descobrindo o meu poder de ataque da mesa*/
        meuDano = getAtaque(lacaios);

        ArrayList<Carta> jogadasMana = new ArrayList<>();

        /*Essa variavel indica se eu estou apto para entrar no modoMana*/
        boolean foundAlvosMana = false;

        int mana = minhaMana;
        int danoDeMagias = 0;
        ArrayList<CartaMagia> cartasMagias = new ArrayList<>();

        /*Percorre as cartas da mao e soma os danos das cartas de dano para tentar dar um dano letal no heroi inimigo*/
        for(int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if(card instanceof CartaMagia && card.getMana() <= mana && ((CartaMagia) card).getMagiaTipo() != TipoMagia.BUFF) {
                danoDeMagias += ((CartaMagia) card).getMagiaDano();
                cartasMagias.add((CartaMagia) card);
                mana -= card.getMana();
            }
        }

        /*Caso eu tenha um ataque letal, adicione-o a jogadasMana*/
        if(danoDeMagias + meuDano >= vidaInimigo) {
            foundAlvosMana = true;
            for(CartaMagia k : cartasMagias) {
                    jogadasMana.add(k);
            }
        }

        /*Caso nao tenha sido encontrado um dano letal, verifica se podemos gastar toda a mana em uma jogada*/
        if(!foundAlvosMana) {
            cascata:
            for (Carta i : mao) {
                /*Para todas as cartas da mao*/
                for (Carta j : mao) {
                    /*Analiso todas as cartas da mao, diferente da que ja estou analisando*/
                    if (i == j) {
                        continue;
                    }
                    for (Carta k : mao) {
                        /*E para todas as analises com a segunda carta, faco uma terceira analise*/
                        if (k == i || k == j) {
                            continue;
                        }
                        /*Em busca de uma combinacao de cartas que de minhaMana*/
                        if (i.getMana() + j.getMana() + k.getMana() == minhaMana) {
                            jogadasMana.add(i);
                            jogadasMana.add(j);
                            jogadasMana.add(k);
                            foundAlvosMana = true;
                            break cascata;
                        }
                    }
                    if (i.getMana() + j.getMana() == minhaMana) {
                        jogadasMana.add(i);
                        jogadasMana.add(j);
                        foundAlvosMana = true;
                        break cascata;
                    }
                }
                if (i.getMana() == minhaMana) {
                    jogadasMana.add(i);
                    foundAlvosMana = true;
                    break;
                }
            }
        }

        /*Caso eu consiga matar o inimigo, priorizar o modo agressivo, ou o inimigo possa me matar*/
        if (meuDano + danoDeMagias >= vidaInimigo || danoVivoNaMesa >= minhaVida) {
            foundAlvosMana = false;
        }

        /*Aqui sera decidido qual comportamento utilizado*/
        if(!foundAlvosMana) {
            if(danoVivoNaMesa >= minhaVida && meuDano + danoDeMagias < vidaInimigo) {
                modoControle(minhasJogadas, vidaInimigo, meuDano);
            }
            else {
                modoAgressivo(minhasJogadas, vidaInimigo, meuDano);
            }
        }
        else {
            modoMana(minhasJogadas, jogadasMana, vidaInimigo, meuDano);
        }
        return minhasJogadas;
    }
}
