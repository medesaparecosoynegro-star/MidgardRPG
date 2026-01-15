package me.ray.midgard.modules.spells.obj;

public enum NodeType {
    ROOT,       // O ponto de partida da árvore
    PASSIVE,    // Nó passivo (antigo CONNECTOR ou standard)
    MUTATION,   // Nivelamento que altera o comportamento (bifurcação)
    SOCKET,     // Engaste para runas
    CONNECTOR,  // Apenas visual (vidro colorido ligando pontos)
    MYTHIC_SKILL // Executa uma MythicMob Skill adicional
}
