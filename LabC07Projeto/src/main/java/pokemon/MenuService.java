package pokemon;

import pokemon.dao.*;
import pokemon.dao.join.PokemonComTipo;
import pokemon.dao.join.RegiaoComPokemon;
import pokemon.model.*;
import java.util.Scanner;
import java.util.*;

public class MenuService {

    //Declarações -------------------------------------------------------
    private Scanner scanner;
    private PokemonDAO pokemonDAO;
    private TreinadorDAO treinadorDAO;
    private AtaquesDAO ataquesDAO;
    private RegiaoDAO regiaoDAO;
    private PokedexDAO pokedexDAO = new PokedexDAO();
    //-------------------------------------------------------------------



    //-------------------------------------------------------------------
    public MenuService() {
        this.scanner = new Scanner(System.in);
        this.pokemonDAO = new PokemonDAO();
        this.treinadorDAO = new TreinadorDAO();
        this.ataquesDAO = new AtaquesDAO();
        this.regiaoDAO = new RegiaoDAO();
    }
    //-------------------------------------------------------------------




    //ESCOLHA -------------------------------------------------------------------
    public void iniciar() {
        while (true) {
            System.out.println("\n=== MENU POKÉMON ===");
            System.out.println("1. Gerenciar Pokémons");
            System.out.println("2. Gerenciar Treinadores");
            System.out.println("3. Gerenciar Ataques");
            System.out.println("4. Gerenciar Regiões");
            System.out.println("5. Gerenciar Pokédex");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    gerenciarPokemons();
                    break;
                case 2:
                    gerenciarTreinadores();
                    break;
                case 3:
                    gerenciarAtaques();
                    break;
                case 4:
                    gerenciarRegioes();
                    break;
                case 5:
                    gerenciarPokedex();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    //-------------------------------------------------------------------



    //POKEMON -------------------------------------------------------------------
    private void gerenciarPokemons() {
        System.out.println("\n=== POKÉMONS ===");
        System.out.println("1. Cadastrar Pokémon");
        System.out.println("2. Listar Pokémons");
        System.out.println("3. Atualizar Pokémon");
        System.out.println("4. Remover Pokémon");
        System.out.println("5. Ver Pokémon com Tipos (JOIN)");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1: // CREATE
                Pokemon novoPokemon = new Pokemon();
                System.out.print("ID: ");
                novoPokemon.setIdPokemon(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Nome: ");
                novoPokemon.setNome(scanner.nextLine());
                System.out.print("Peso (kg): ");
                novoPokemon.setPeso(scanner.nextInt());
                System.out.print("Altura (cm): ");
                novoPokemon.setAltura(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Nature: ");
                novoPokemon.setNature(scanner.nextLine());
                System.out.print("Stats: ");
                novoPokemon.setStats(scanner.nextInt());
                System.out.print("ID do Treinador: ");
                novoPokemon.setTreinadorId(scanner.nextInt());
                System.out.print("ID da Região: ");
                novoPokemon.setRegiaoId(scanner.nextInt());

                if (pokemonDAO.insertPokemon(novoPokemon)) {
                    System.out.println("Pokémon cadastrado com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar.");
                }
                break;

            case 2: // READ
                List<Pokemon> pokemons = pokemonDAO.selectAllPokemon();
                System.out.println("\nLista de Pokémons:");
                for (Pokemon p : pokemons) {
                    System.out.printf(
                            "ID: %d | Nome: %s | Peso: %dkg | Altura: %dcm%n",
                            p.getIdPokemon(), p.getNome(), p.getPeso(), p.getAltura()
                    );
                }
                break;

            case 3: // UPDATE
                System.out.print("Digite o ID do Pokémon para atualizar: ");
                int idAtualizar = scanner.nextInt();
                scanner.nextLine();

                Pokemon pokemonAtualizar = pokemonDAO.selectPokemonById(idAtualizar);
                if (pokemonAtualizar == null) {
                    System.out.println("Pokémon não encontrado!");
                    break;
                }

                System.out.print("Novo nome (" + pokemonAtualizar.getNome() + "): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    pokemonAtualizar.setNome(novoNome);
                }

                System.out.print("Novo peso (" + pokemonAtualizar.getPeso() + "kg): ");
                String novoPeso = scanner.nextLine();
                if (!novoPeso.isEmpty()) {
                    pokemonAtualizar.setPeso(Integer.parseInt(novoPeso));
                }

                if (pokemonDAO.updatePokemon(idAtualizar, pokemonAtualizar)) {
                    System.out.println("Pokémon atualizado!");
                } else {
                    System.out.println("Falha na atualização.");
                }
                break;

            case 4: // DELETE
                System.out.print("Digite o ID do Pokémon para remover: ");
                int idRemover = scanner.nextInt();

                if (pokemonDAO.deletePokemon(idRemover)) {
                    System.out.println("Pokémon removido!");
                } else {
                    System.out.println("Erro ao remover.");
                }
                break;

            case 5: // JOIN (Pokémon + Tipos)
                System.out.println("\nPokémons e seus Tipos:");
                List<PokemonComTipo> pokemonsComTipos = pokemonDAO.listarPokemonsETipos();

                for (PokemonComTipo pct : pokemonsComTipos) {
                    System.out.printf(
                            "%s (ID: %d) - Tipo: %s%n",
                            pct.getNomePokemon(),
                            pct.getIdPokemon(),
                            pct.getNomeTipo()
                    );
                }
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
    //-------------------------------------------------------------------



    //TREINADORES -------------------------------------------------------------------
    private void gerenciarTreinadores() {
        System.out.println("\n=== TREINADORES ===");
        System.out.println("1. Cadastrar Treinador");
        System.out.println("2. Listar Treinadores");
        System.out.println("3. Atualizar Treinador");
        System.out.println("4. Remover Treinador");
        System.out.println("5. Ver Treinadores com Pokémons (JOIN)");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1: // CREATE
                Treinador novoTreinador = new Treinador();
                System.out.print("ID do Treinador: ");
                novoTreinador.setIdTreinador(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Nome: ");
                novoTreinador.setNome(scanner.nextLine());
                System.out.print("Quantidade de Pokébolas: ");
                novoTreinador.setQtdPokebolas(scanner.nextInt());
                System.out.print("Quantidade de Potions: ");
                novoTreinador.setQtdPotion(scanner.nextInt());
                System.out.print("ID da Pokédex: ");
                novoTreinador.setPokedexId(scanner.nextInt());

                if (treinadorDAO.insertTreinador(novoTreinador)) {
                    System.out.println("Treinador cadastrado com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar.");
                }
                break;

            case 2: // READ
                List<Treinador> treinadores = treinadorDAO.selectAllTreinadores();
                System.out.println("\nLista de Treinadores:");
                for (Treinador t : treinadores) {
                    System.out.printf(
                            "ID: %d | Nome: %s | Pokébolas: %d | Potions: %d%n",
                            t.getIdTreinador(), t.getNome(), t.getQtdPokebolas(), t.getQtdPotion()
                    );
                }
                break;

            case 3: // UPDATE
                System.out.print("Digite o ID do Treinador para atualizar: ");
                int idAtualizar = scanner.nextInt();
                scanner.nextLine();

                Treinador treinadorAtualizar = treinadorDAO.selectTreinadorById(idAtualizar);
                if (treinadorAtualizar == null) {
                    System.out.println("Treinador não encontrado!");
                    break;
                }

                System.out.print("Novo nome (" + treinadorAtualizar.getNome() + "): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    treinadorAtualizar.setNome(novoNome);
                }

                System.out.print("Novas Pokébolas (" + treinadorAtualizar.getQtdPokebolas() + "): ");
                String novasPokebolas = scanner.nextLine();
                if (!novasPokebolas.isEmpty()) {
                    treinadorAtualizar.setQtdPokebolas(Integer.parseInt(novasPokebolas));
                }

                if (treinadorDAO.updateTreinador(idAtualizar, treinadorAtualizar)) {
                    System.out.println("Treinador atualizado!");
                } else {
                    System.out.println("Falha na atualização.");
                }
                break;

            case 4: // DELETE
                System.out.print("Digite o ID do Treinador para remover: ");
                int idRemover = scanner.nextInt();

                if (treinadorDAO.deleteTreinador(idRemover)) {
                    System.out.println("Treinador removido!");
                } else {
                    System.out.println("Erro ao remover.");
                }
                break;

            case 5:
                gerenciarPokedex();
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
    //-------------------------------------------------------------------



    //ATAQUES -------------------------------------------------------------------
    private void gerenciarAtaques() {
        System.out.println("\n=== ATAQUES ===");
        System.out.println("1. Cadastrar Ataque");
        System.out.println("2. Listar Ataques");
        System.out.println("3. Atualizar Ataque");
        System.out.println("4. Remover Ataque");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1: // CREATE
                Ataques novoAtaque = new Ataques();
                System.out.print("ID do Ataque: ");
                novoAtaque.setIdAtaques(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Nome: ");
                novoAtaque.setNome(scanner.nextLine());
                System.out.print("Força: ");
                novoAtaque.setForca(scanner.nextInt());

                if (ataquesDAO.insertAtaque(novoAtaque)) {
                    System.out.println("Ataque cadastrado!");
                } else {
                    System.out.println("Erro ao cadastrar.");
                }
                break;

            case 2: // READ
                List<Ataques> ataques = ataquesDAO.selectAtaques();
                System.out.println("\n=== LISTA DE ATAQUES ===");
                for (Ataques a : ataques) {
                    System.out.println(a.getIdAtaques() + " | " + a.getNome() + " (Força: " + a.getForca() + ")");
                }
                break;

            case 3: // UPDATE
                System.out.print("Digite o ID do Ataque para atualizar: ");
                int idAtualizar = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Novo nome (deixe em branco para manter): ");
                String novoNome = scanner.nextLine();

                System.out.print("Nova força (digite 0 para manter): ");
                int novaForca = scanner.nextInt();

                if (ataquesDAO.updateAtaque(idAtualizar, novoNome, novaForca)) {
                    System.out.println("✔ Ataque atualizado!");
                } else {
                    System.out.println("✖ Falha na atualização.");
                }
                break;

            case 4: // DELETE
                System.out.print("Digite o ID do Ataque para remover: ");
                int idRemover = scanner.nextInt();

                if (ataquesDAO.deleteAtaque(idRemover)) {
                    System.out.println("Ataque removido!");
                } else {
                    System.out.println("Erro ao remover.");
                }
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
    //-------------------------------------------------------------------



    //REGIOES -------------------------------------------------------------------
    private void gerenciarRegioes() {
        System.out.println("\n=== REGIÕES ===");
        System.out.println("1. Cadastrar Região");
        System.out.println("2. Listar Regiões");
        System.out.println("3. Atualizar Região");
        System.out.println("4. Remover Região");
        System.out.println("5. Ver Regiões com Pokémons (JOIN)");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1: // CREATE
                Regiao novaRegiao = new Regiao();
                System.out.print("ID da Região: ");
                novaRegiao.setIdRegiao(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Nome: ");
                novaRegiao.setNome(scanner.nextLine());

                if (regiaoDAO.insertRegiao(novaRegiao)) {
                    System.out.println("✔ Região cadastrada!");
                } else {
                    System.out.println("✖ Erro ao cadastrar.");
                }
                break;

            case 2: // READ
                List<Regiao> regioes = regiaoDAO.selectAllRegioes();
                System.out.println("\n=== LISTA DE REGIÕES ===");
                for (Regiao r : regioes) {
                    System.out.println(r.getIdRegiao() + " | " + r.getNome());
                }
                break;

            case 3: // UPDATE
                System.out.print("Digite o ID da Região para atualizar: ");
                int idAtualizar = scanner.nextInt();
                scanner.nextLine();

                // Primeiro busca a região existente
                Regiao regiaoAtual = regiaoDAO.selectRegiaoById(idAtualizar); // Você precisará implementar este método
                if (regiaoAtual == null) {
                    System.out.println("✖ Região não encontrada!");
                    break;
                }

                System.out.print("Novo nome (" + regiaoAtual.getNome() + "): ");
                String novoNome = scanner.nextLine();

                // Atualiza apenas se o usuário digitou algo
                if (!novoNome.isEmpty()) {
                    regiaoAtual.setNome(novoNome);
                }

                if (regiaoDAO.updateRegiao(idAtualizar, regiaoAtual)) {
                    System.out.println("✔ Região atualizada!");
                } else {
                    System.out.println("✖ Falha na atualização.");
                }
                break;

            case 4: // DELETE
                System.out.print("Digite o ID da Região para remover: ");
                int idRemover = scanner.nextInt();

                if (regiaoDAO.deleteRegiao(idRemover)) {
                    System.out.println("✔ Região removida!");
                } else {
                    System.out.println("✖ Erro ao remover.");
                }
                break;

            case 5: // JOIN
                System.out.println("\nPokémons por Região:");
                List<RegiaoComPokemon> resultados = regiaoDAO.listarRegioesComPokemons();

                if (resultados.isEmpty()) {
                    System.out.println("Nenhum Pokémon cadastrado nas regiões!");
                } else {
                    for (RegiaoComPokemon rp : resultados) {
                        System.out.printf(
                                "%s ➔ %s (ID: %d)%n",
                                rp.getNomeRegiao(),
                                rp.getNomePokemon(),
                                rp.getIdPokemon()
                        );
                    }
                }
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
    //-------------------------------------------------------------------



    //POKEDEX-------------------------------------------------------------------
    private void gerenciarPokedex() {
        System.out.println("\n=== MENU POKÉDEX ===");
        System.out.println("1. Cadastrar nova Pokédex");
        System.out.println("2. Listar todas");
        System.out.println("3. Atualizar Pokédex");
        System.out.println("4. Remover Pokédex");
        System.out.print("Escolha: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1: // Cadastrar
                Pokedex nova = new Pokedex();
                System.out.print("ID: ");
                nova.setIdPokedex(scanner.nextInt());
                System.out.print("Número de Pokémons: ");
                nova.setNumPkmRegistrados(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Descrição: ");
                nova.setDescricao(scanner.nextLine());

                if (pokedexDAO.insertPokedex(nova)) {
                    System.out.println("Cadastrado com sucesso!");
                } else {
                    System.out.println("Erro ao cadastrar.");
                }
                break;

            case 2: // Listar
                List<Pokedex> lista = pokedexDAO.selectPokedex();
                System.out.println("\nLISTA DE POKÉDEX:");
                for (Pokedex p : lista) {
                    System.out.println("ID: " + p.getIdPokedex() +
                            " | Pokémons: " + p.getNumPkmRegistrados() +
                            " | Descrição: " + p.getDescricao());
                }
                break;

            case 3: // Atualizar
                System.out.print("Digite o ID da Pokédex: ");
                int idAtualizar = scanner.nextInt();
                Pokedex atualizar = new Pokedex();
                atualizar.setIdPokedex(idAtualizar);
                System.out.print("Novo número de Pokémons: ");
                atualizar.setNumPkmRegistrados(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Nova descrição: ");
                atualizar.setDescricao(scanner.nextLine());

                if (pokedexDAO.updatePokedex(idAtualizar, atualizar)) {
                    System.out.println("Atualizado com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar.");
                }
                break;

            case 4: // Remover
                System.out.print("Digite o ID para remover: ");
                int idRemover = scanner.nextInt();

                if (pokedexDAO.deletePokedex(idRemover)) {
                    System.out.println("Removido com sucesso!");
                } else {
                    System.out.println("Erro ao remover.");
                }
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }
    //-------------------------------------------------------------------

}