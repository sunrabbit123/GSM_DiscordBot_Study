package db

import (
	"context"
	"fmt"
	"log"
	"strings"
	"time"

	"github.com/bwmarrin/discordgo"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

func InsertCommand(client *mongo.Client, content []string) {
	coll := client.Database("gogo").Collection("userCommand")

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	_, err := coll.InsertOne(ctx, bson.M{"res": content[0], "req": strings.Join(content[1:], " ")})

	if err != nil{
		log.Fatal(err)
	} else {
		fmt.Println("데이터 삽입에 성공하였습니다.")
		fmt.Printf("삽입 커맨드| %s : %s", content[0], content[1:])
		fmt.Println()
	}
}

func DeleteCommand(client *mongo.Client, content string) {
	coll := client.Database("gogo").Collection("userCommand")

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	_, err := coll.DeleteOne(ctx, bson.M{"res": content[0]})

	if err != nil{
		log.Fatal(err)
	} else {
		fmt.Println("데이터 삭제에 성공하였습니다.")
		fmt.Printf("삭제 커맨드| %s", content[0])
		fmt.Println()
	}
}

//얼
func SelectCommand(client *mongo.Client, content string, s *discordgo.Session, m *discordgo.MessageCreate) {
	coll := client.Database("gogo").Collection("userCommand")

	var result map[string]string

	filter := bson.M{ "res" : content }
	fmt.Println(content)

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	err := coll.FindOne(ctx, filter).Decode(&result)

	req := result["req"]

	if err != nil{
		fmt.Println(err)
	} else {
		fmt.Println("데이터 조회에 성공하였습니다.")
		fmt.Printf("조회 커맨드| %s : %s", content, req)
		fmt.Println()
		s.ChannelMessageSend(m.ChannelID, req)
	}
}

func SelectAll (client *mongo.Client, content string) {
	//모든 명령어 검색
}