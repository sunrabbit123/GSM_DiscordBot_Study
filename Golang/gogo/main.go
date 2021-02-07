package main

import (
	"context"
	"fmt"
	"log"
	"os"
	"os/signal"
	"strings"
	"syscall"
	"time"

	"discord/Golang/gogo/db"

	"github.com/bwmarrin/discordgo"
	"github.com/joho/godotenv"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"go.mongodb.org/mongo-driver/mongo/readpref"
)

func init() {
	err := godotenv.Load(".env")

	if err != nil {
		log.Fatal("Error loading .env file")
	}
}

func main() {
	startBot()
}

func startBot() {
	dg, err := discordgo.New("Bot " + os.Getenv("Token"))
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
	// 문자 판단
	content := strings.Fields(m.Content)
	userCommand := strings.Join(content[1:], " ")

	if content[0] != "고" && 
	content[0] != "ㄱ" && 
	content[0] != "고커추" && 
	content[0] != "고커삭" {
		return
	}

	//db 연결
	fmt.Println("연결 시작")

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()
	client, err := mongo.Connect(ctx, options.Client().ApplyURI(os.Getenv("URL")))

	defer func() {
    if err = client.Disconnect(ctx); err != nil {
			panic(err)
		}
		fmt.Println("연결이 해지되었습니다.")
	}()

	ctx, cancel = context.WithTimeout(context.Background(), 8*time.Second)
	defer cancel()
	err = client.Ping(ctx, readpref.Primary())
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println("성공적으로 연결되었습니다.")
	}
	
	// 유저 커맨드
	if m.Author.ID == s.State.User.ID {
		return
	}

	if userCommand == "ping" && content[0] == "ㄱ" && content[0] == "고"{
		s.ChannelMessageSend(m.ChannelID, "Pong!")
		return
	}

	if userCommand == "pong" && content[0] == "ㄱ" && content[0] == "고"{
		s.ChannelMessageSend(m.ChannelID, "Ping!")
		return
	}

	// if userCommand == "목록" {
	// 	db.SelectAll(client, userCommand, s, m)
	// 	return
	// }

	switch(content[0]) {
	case "고": 
		req := db.SelectCommand(client, userCommand)
		s.ChannelMessageSend(m.ChannelID, req)
		return
	case "ㄱ": 
		req := db.SelectCommand(client, userCommand)
		s.ChannelMessageSend(m.ChannelID, req)
		return
	case "고커추": 
		db.InsertCommand(client, content[1:])
		return
	case "고커삭":
		req := db.SelectCommand(client, userCommand)
		s.ChannelMessageSend(m.ChannelID, req)
		return
	default:
		return
	}
}
