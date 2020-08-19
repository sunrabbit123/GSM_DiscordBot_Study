package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"github.com/bwmarrin/discordgo"
)

var (
	Token string
)

func init() {

	flag.StringVar(&Token, "t", "", "NzAwOTYzNzM4OTY2MTYzNDg3.XpqlWg.b1I9N8fllJ3p_3CYMqWDExoytl0")
	flag.Parse()
}

func main() {
	dg, err := discordgo.New("Bot " + "NzAwOTYzNzM4OTY2MTYzNDg3.XpqlWg.b1I9N8fllJ3p_3CYMqWDExoytl0")
	if err != nil {
		fmt.Println("error creating Discord session,", err)
		return
	}
	dg.AddHandler(messageCreate)
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
	if m.Content == "ㄱㄱ안녕" {
		s.ChannelMessageSend(m.ChannelID, "안녕!")
	}
	if m.Content == "ㄱㄱ굴러" {
		s.ChannelMessageSend(m.ChannelID, "데굴")
	}
}