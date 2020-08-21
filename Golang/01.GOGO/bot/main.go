package main

import (
	"account"
	"database/sql"
	"flag"
	"fmt"
	"os"
	"os/signal"
	"strings"
	"syscall"
	"token"

	embed "github.com/Clinet/discordgo-embed"
	"github.com/bwmarrin/discordgo"
	_ "github.com/go-sql-driver/mysql"
)

func init() {
	flag.Parse()
}

var (
	addCommand string = "고커추"
	prefix     string = "고"
)

func main() {
	startBot()
}

func startBot() {
	dg, err := discordgo.New("Bot " + token.Token)
	if err != nil {
		fmt.Println("불일치 세션 생성 오류,", err)
		return
	}
	dg.AddHandler(command)
	dg.Identify.Intents = discordgo.MakeIntent(discordgo.IntentsGuildMessages)
	err = dg.Open()
	if err != nil {
		fmt.Println("연결 오류,", err)
		return
	}
	fmt.Println("봇이 시작합니다. CTRL-C을 눌러 나가기.")
	sc := make(chan os.Signal, 1)
	signal.Notify(sc, syscall.SIGINT, syscall.SIGTERM, os.Interrupt, os.Kill)
	<-sc
	dg.Close()
}

func command(s *discordgo.Session, m *discordgo.MessageCreate) {
	db, err := sql.Open("mysql", account.Account)
	if err != nil {
		panic(err.Error())
	}
	defer func() {
		recover()
	}()
	defer db.Close()
	if m.Author.ID == s.State.User.ID {
		return
	}

	prefixFilter := strings.Fields(m.Content)
	if string(prefixFilter[0]) == prefix {
		var CReq string
		_, err := db.Exec("SELECT CRes FROM command WHERE CReq = ?;", prefixFilter[1])
		if err == nil {
			rowErr := db.QueryRow("SELECT CRes FROM command WHERE CReq = ?;", prefixFilter[1]).Scan(&CReq)
			if rowErr != nil {
				panic(err.Error())
			}
			s.ChannelMessageSend(m.ChannelID, CReq)
		} else {
			s.ChannelMessageSend(m.ChannelID, "존재하지 않는 명령어입니다.")
		}
	}

	addCommandFilter := strings.Fields(m.Content)
	if string(addCommandFilter[0]) == addCommand {
		_, err := db.Exec("INSERT INTO usercommand (CReq, CRes) VALUES (?, ?);", addCommandFilter[1], strings.Join(addCommandFilter[2:], " "))
		if err != nil {
			panic(err.Error())
		}
		s.ChannelMessageSend(m.ChannelID, "추가 완료!")
	}

	var userCReq string
	userCommand := m.Content
	_, userCommandErr := db.Exec("SELECT CRes FROM usercommand WHERE CReq = ?;", userCommand)
	if userCommandErr == nil {
		userCommandErr2 := db.QueryRow("SELECT CRes FROM usercommand WHERE CReq = ?;", userCommand).Scan(&userCReq)
		if userCommandErr2 != nil {
			fmt.Println(err.Error())
		}
		s.ChannelMessageSend(m.ChannelID, userCReq)
	}
}

func createEmbed(s *discordgo.Session, m *discordgo.Message) {
	embed.NewEmbed()
}
