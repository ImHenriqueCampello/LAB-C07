DROP DATABASE PokemonDB;
CREATE DATABASE IF NOT EXISTS PokemonDB;
USE PokemonDB;

-- Tabelas --
CREATE TABLE IF NOT EXISTS Regiao (
    idRegiao INT PRIMARY KEY,
    nome VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS Pokedex (
    idPokedex INT PRIMARY KEY,
    numPkmRegistrados INT,
    descricao VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS Treinador (
    idTreinador INT PRIMARY KEY,
    nome VARCHAR(45),
    qtdPokebolas INT,
    qtdPotion INT,
    Pokedex_idPokedex INT,
    FOREIGN KEY (Pokedex_idPokedex) REFERENCES Pokedex(idPokedex)
);

CREATE TABLE IF NOT EXISTS Insignia (
    idInsignia INT PRIMARY KEY,
    nome VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS Insignia_has_Treinador (
    Insignia_idInsignia INT,
    Treinador_idTreinador INT,
    PRIMARY KEY (Insignia_idInsignia, Treinador_idTreinador),
    FOREIGN KEY (Insignia_idInsignia) REFERENCES Insignia(idInsignia),
    FOREIGN KEY (Treinador_idTreinador) REFERENCES Treinador(idTreinador)
);

CREATE TABLE IF NOT EXISTS Pokemon (
    idPokemon INT PRIMARY KEY,
    nome VARCHAR(45),
    peso INT,
    altura INT,
    nature VARCHAR(45),
    stats INT,
    Treinador_idTreinador INT,
    Regiao_idRegiao INT,
    FOREIGN KEY (Treinador_idTreinador) REFERENCES Treinador(idTreinador),
    FOREIGN KEY (Regiao_idRegiao) REFERENCES Regiao(idRegiao)
);

CREATE TABLE IF NOT EXISTS Ataques (
    idAtaques INT PRIMARY KEY,
    nome VARCHAR(45),
    forca INT
);

CREATE TABLE IF NOT EXISTS Pokemon_has_Ataques (
    Pokemon_idPokemon INT,
    Ataques_idAtaques INT,
    PRIMARY KEY (Pokemon_idPokemon, Ataques_idAtaques),
    FOREIGN KEY (Pokemon_idPokemon) REFERENCES Pokemon(idPokemon),
    FOREIGN KEY (Ataques_idAtaques) REFERENCES Ataques(idAtaques)
);

CREATE TABLE IF NOT EXISTS Tipos (
    idTipoPokemon INT PRIMARY KEY,
    nomeTipo VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS Pokemon_has_Tipos (
    Pokemon_idPokemon INT,
    TipoPokemon_idTipoPokemon INT,
    PRIMARY KEY (Pokemon_idPokemon, TipoPokemon_idTipoPokemon),
    FOREIGN KEY (Pokemon_idPokemon) REFERENCES Pokemon(idPokemon),
    FOREIGN KEY (TipoPokemon_idTipoPokemon) REFERENCES Tipos(idTipoPokemon)
);
-- Fim das tabelas --

-- Insert --
INSERT INTO Regiao VALUES (1, 'Kantto'), (2, 'Johtto');
INSERT INTO Pokedex VALUES (1, 150, 'Kanto'), (2, 250, 'Johto');
INSERT INTO Treinador VALUES (1, 'Ash', 5, 2, 1), (2, 'Misty', 4, 1, 2);
INSERT INTO Insignia VALUES (1, 'Pedra'), (2, 'Agua');
INSERT INTO Insignia_has_Treinador VALUES (1, 1), (2, 2);
INSERT INTO Pokemon VALUES (1, 'Pikachu', 60, 40, 'Brave', 320, 1, 1), (2, 'Staryu', 340, 80, 'Calm', 290, 2, 2);
INSERT INTO Ataques VALUES (1, 'Thunderbolt', 90), (2, 'Water Gun', 40);
INSERT INTO Pokemon_has_Ataques VALUES (1, 1), (2, 2);
INSERT INTO Tipos VALUES (1, 'Eletricoooo'), (2, 'Aaagua');
INSERT INTO Pokemon_has_Tipos VALUES (1, 1), (2, 2);
-- Insert Fim --

-- Updates --
UPDATE Regiao SET nome = 'Kanto' WHERE idRegiao = 1;
UPDATE Regiao SET nome = 'Johto' WHERE idRegiao = 2;

UPDATE Pokedex SET numPkmRegistrados = 151 WHERE idPokedex = 1;
UPDATE Pokedex SET numPkmRegistrados = 251 WHERE idPokedex = 2;

UPDATE Treinador SET qtdPokebolas = 10 WHERE idTreinador = 1;
UPDATE Treinador SET nome = 'Misty de Cerulean' WHERE idTreinador = 2;

UPDATE Insignia SET nome = 'Rocha' WHERE idInsignia = 1;
UPDATE Insignia SET nome = 'Cascata' WHERE idInsignia = 2;

UPDATE Pokemon SET stats = 330 WHERE idPokemon = 1;
UPDATE Pokemon SET nome = 'Shiny Staryu' WHERE idPokemon = 2;

UPDATE Ataques SET forca = 95 WHERE idAtaques = 1;
UPDATE Ataques SET nome = 'Water Pulse' WHERE idAtaques = 2;

UPDATE Tipos SET nomeTipo = 'Eletrico' WHERE idTipoPokemon = 1;
UPDATE Tipos SET nomeTipo = 'Agua' WHERE idTipoPokemon = 2;
-- Updates Fim --

-- Join --
-- 1: Treinador com Pokedex e Pokemon
SELECT t.nome, p.nome, d.numPkmRegistrados
FROM Treinador t
JOIN Pokemon p ON t.idTreinador = p.Treinador_idTreinador
JOIN Pokedex d ON t.Pokedex_idPokedex = d.idPokedex;

-- 2: Pokemon com seus tipos e ataques
SELECT pk.nome, tp.nomeTipo, at.nome
FROM Pokemon pk
JOIN Pokemon_has_Tipos pht ON pk.idPokemon = pht.Pokemon_idPokemon
JOIN Tipos tp ON pht.TipoPokemon_idTipoPokemon = tp.idTipoPokemon
JOIN Pokemon_has_Ataques pha ON pk.idPokemon = pha.Pokemon_idPokemon
JOIN Ataques at ON pha.Ataques_idAtaques = at.idAtaques;

-- 3: Treinador com insignias e regiao
SELECT t.nome, i.nome, r.nome
FROM Treinador t
JOIN Insignia_has_Treinador iht ON t.idTreinador = iht.Treinador_idTreinador
JOIN Insignia i ON iht.Insignia_idInsignia = i.idInsignia
JOIN Pokemon p ON t.idTreinador = p.Treinador_idTreinador
JOIN Regiao r ON p.Regiao_idRegiao = r.idRegiao;
-- Join Fim --

-- Ultimo Requerimento:
-- (1) View: Treinadores e número de Pokémons (Possibilidade de ver quantos pokemons cada treinador possui)
DROP VIEW IF EXISTS vw_Treinadores_Pokemons;

CREATE VIEW vw_Treinadores_Pokemons AS
SELECT t.nome AS Treinador, COUNT(p.idPokemon) AS NumPokemons
FROM Treinador t
LEFT JOIN Pokemon p ON t.idTreinador = p.Treinador_idTreinador
GROUP BY t.nome;


-- (2) Function: (Você consegue saber qual pokemon possui ataques mais fortes, essa função soma a força
--  dos ataques dos pokemons)
DELIMITER $$

DROP FUNCTION IF EXISTS TotalForcaAtaques $$

CREATE FUNCTION TotalForcaAtaques(pokemonId INT) RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE total INT DEFAULT 0;

	SELECT COALESCE(SUM(a.forca), 0) INTO total
	FROM Ataques a
	JOIN Pokemon_has_Ataques pha ON a.idAtaques = pha.Ataques_idAtaques
	WHERE pha.Pokemon_idPokemon = pokemonId;

	RETURN total;
END $$

DELIMITER ;

-- Delete --
DELETE FROM Pokemon_has_Tipos WHERE Pokemon_idPokemon = 2;
DELETE FROM Pokemon_has_Tipos WHERE Pokemon_idPokemon = 1;

DELETE FROM Tipos WHERE idTipoPokemon = 1;
DELETE FROM Tipos WHERE idTipoPokemon = 2;

DELETE FROM Pokemon_has_Ataques WHERE Pokemon_idPokemon = 1;
DELETE FROM Pokemon_has_Ataques WHERE Pokemon_idPokemon = 2;

DELETE FROM Ataques WHERE idAtaques = 1;
DELETE FROM Ataques WHERE idAtaques = 2;

DELETE FROM Pokemon WHERE idPokemon = 1;
DELETE FROM Pokemon WHERE idPokemon = 2;

DELETE FROM Insignia_has_Treinador WHERE Insignia_idInsignia = 1;
DELETE FROM Insignia_has_Treinador WHERE Insignia_idInsignia = 2;

DELETE FROM Insignia WHERE idInsignia = 1;
DELETE FROM Insignia WHERE idInsignia = 2;

DELETE FROM Treinador WHERE idTreinador = 1;
DELETE FROM Treinador WHERE idTreinador = 2;

DELETE FROM Pokedex WHERE idPokedex = 1;
DELETE FROM Pokedex WHERE idPokedex = 2;

DELETE FROM Regiao WHERE idRegiao = 1;
DELETE FROM Regiao WHERE idRegiao = 2;
-- Delete Fim --
