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

	content := strings.Fields(m.Content)
	userCommand := strings.Join(content[1:], " ")

	fmt.Println(content[0])

	if userCommand == "ping" {
		s.ChannelMessageSend(m.ChannelID, "Pong!")
		return
	}

	if userCommand == "pong" {
		s.ChannelMessageSend(m.ChannelID, "Ping!")
		return
	}

	if userCommand == "목록" {
		db.SelectAll(client, userCommand)
	}

	switch(content[0]) {
	case "고": 
		db.SelectCommand(client, userCommand, s, m)
		break
	case "ㄱ": 
		db.SelectCommand(client, userCommand, s, m)
		break
	case "고커추": 
		db.InsertCommand(client, content[1:])
	case "고커삭":
		db.DeleteCommand(client, userCommand)
		break
	default:
		return
	}

	// fmt.Println(userCommand)
}
