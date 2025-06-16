# Minecraft Nats Bridge

A Minecraft server plugin that creates a bidirectional communication bridge to a NATS.io messaging system. Integrate your server with external applications seamlessly.

```
[Minecraft Server] <--> [NatsBridge Plugin] <--> [NATS.io] <--> [Your Services]
```

## ✨ Features

*   **Remote Command Execution:** Execute commands on your server from any NATS-enabled application.
*   **Real-time Events:** Subscribe to server events like player joins, leaves, chats, and more.
*   **Player Status Queries:** Get a list of online players and their status on demand.
*   **Simple JSON Protocol:** Easy to integrate with any programming language.

## 🚀 Quick Start

1.  Download the latest `.jar` from the [**Releases**](https://github.com/YOUR_USERNAME/NatsBridge/releases) page.
2.  Place it in your server's `plugins` directory.
3.  Configure `config.yml` with your NATS server details and restart the server.

For detailed setup and configuration, please refer to our full documentation.

## 🛠️ Usage Example

To run a command, publish a message to the `natsbridge.commands` subject:

```json
{
  "command": "give Steve diamond 64"
}
```

To receive player join events, subscribe to `natsbridge.events.player.join`:

```json
{
  "event": "player_join",
  "player": {
    "name": "Steve",
    "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5"
  },
  "timestamp": 1677611223
}
```

## 📚 Documentation

This README provides a brief overview. For comprehensive information on architecture, configuration, and contribution guidelines, please visit the **[Official Documentation](https://deepwiki.com/ProjectFasberry/minecraft-nats/1-overview)**.

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.
