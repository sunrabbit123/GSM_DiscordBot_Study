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

	"github.com/bwmarrin/discordgo"
	"github.com/go-sql-driver/mysql"
)


func init() {
	lag.Parse()
}

var (
	addCommand    string = "고커추"
	prefix        string = "고"
	deleteCommand        = "고커삭"

)

func main(){
	tartBot()
}

func startBot() {
	dg, err := discrdgo.New("Bot " + token.Token)
	if err != nil {
		fmt.Prntln("불일치 세션 생성 오류,", err)
		eturn
	}
	dg.AddHandler(command)
	dg.Identify.Intnts = discordgo.MakeIntent(discordgo.IntentsGuildMessages)
	err = dg.Open()
	if err != nil {
		fmt.Prntln("연결 오류,", err)
		eturn
	}
	fmt.Println("봇이 시작합니다. CTRL-C 눌러 나가기.")
	sc := make(chan os.Signal, 1)
	signl.Notify(sc, syscall.SIGINT, syscall.SIGTERM, os.Interrupt, os.Kill)
	<-sc
	g.Close()
}


func command(s *discordgo.Session, m *discordg.MessageCreate) {
	db, err := sql.Open("mysql", account.Account)
	if err != nil {
		anic(err.Error())
	}
	defer func() {
		reover()
	}()
	defer db.Close()
	if m.Auhor.ID == s.State.User.ID {
		return
}

if m.Auhor.Bot == true {
	return 0

	massage := m.Content

	filter := strings.Fields(massage)

	fmt.Println(s.State.User.Bot)

	if s.State.User.Bot == true {

		if string(filter[0]) == prefix {
			var CReq string
			_, err := db.Exec("SELECT CRes FROM command WHERE CReq = ?;", filter[1])
			if err == nil {
				rowErr := db.QueryRow("SELECT CRes FROM command WHERE CReq = ?;", filter[1]).Scan(&CReq)
				if rowErr != nil {
					panic(err.Error())
				}
				s.ChannelMessageSend(m.ChannelID, CReq)
			} else {
				s.ChannelMessageSend(m.ChannelID, "존재하지 않는 명령어입니다.")
			}

		}

		if string(filter[0]) == addCommand {
			if filter[1] == "고" {
				s.ChannelMessageSend(m.ChannelID, "고는 사용할 수 없습니다.")
			} else if len(filter) == 2 {
				s.ChannelMessageSend(m.ChannelID, "고커추 형식은 ```고커추 '명령어' '반응할 말'```입니다.")
			} else {
				_, err := db.Exec("INSERT INTO usercommand (CReq, CRes) VALUES (?, ?);", filter[1], strings.Join(filter[2:], " "))
				if err != nil {
					panic(err.Error())
				}
				s.ChannelMessageSend(m.ChannelID, "추가가 성공적으로 완료되었습니다.")
			}
		}
=
		if string(filter[0]) == deleteCommand {
			_, err := db.Exec("DELETE FROM usercommand WHERE CReq = ?;", strings.Join(filter[1:], " "))
			if err != nil {
				panic(err.Error())
			}
			s.ChannelMessageSend(m.ChannelID, "삭제 완료!")
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
	} else {
		s.ChannelMessageSend(m.ChannelID, "우왕 봇이다")
	}
}
