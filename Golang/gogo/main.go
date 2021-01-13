package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"
	"context"
	"log"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"github.com/bwmarrin/discordgo"
)

var (
	Token string
)

func init() {

	flag.StringVar(&Token, "t", "", "NzAwOTYzNzM4OTY2MTYzNDg3.XpqlWg.CiXGTu2M_NpJcm19lbFfwPl0-iE")
	flag.Parse()
}

func main() {
	dg, err := discordgo.New("Bot " + "NzAwOTYzNzM4OTY2MTYzNDg3.XpqlWg.CiXGTu2M_NpJcm19lbFfwPl0-iE")
	if err != nil {
		fmt.Println("error creating Discord session,", err)
		return
	}

	dg.AddHandler(messageCreate)

	dg.Identify.Intents = discordgo.MakeIntent(discordgo.IntentsGuildMessages)

	err = dg.Open()
	if err != nil {
		fmt.Println("error opening connection,", err)
		return
	}

	fmt.Println("Bot is now running.  Press CTRL-C to exit.")
	sc := make(chan os.Signal, 1)
	signal.Notify(sc, syscall.SIGINT, syscall.SIGTERM, os.Interrupt, os.Kill)
	<-sc

	dg.Close()
}

func messageCreate(s *discordgo.Session, m *discordgo.MessageCreate) {
	if m.Author.ID == s.State.User.ID {
		return
	}

	if m.Content == "ping" {
		s.ChannelMessageSend(m.ChannelID, "Pong!")
	}

	if m.Content == "pong" {
		s.ChannelMessageSend(m.ChannelID, "Ping!")
	}
}
