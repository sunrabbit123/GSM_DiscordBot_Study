package db

import (
	"context"
	"fmt"
	"log"
	"strings"
	"time"

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

func DeleteCommand(client *mongo.Client, content string) string {
	coll := client.Database("gogo").Collection("userCommand")

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	_, err := coll.DeleteOne(ctx, bson.M{"res": content})

	if err != nil{
		if err == nil {
			return "해당 명령어가 존재하지 않아요!"
		} else {
			fmt.Println(err.Error())
		}
	}
	fmt.Println("데이터 삭제에 성공하였습니다.")
	fmt.Printf("삭제 커맨드| %s", content)
	fmt.Println()
	return "커맨드가 삭제되었어요!"
}

func SelectCommand(client *mongo.Client, content string) string {
	coll := client.Database("gogo").Collection("userCommand")

	var result map[string]string

	filter := bson.M{ "res" : content }
	fmt.Println(content)

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	err := coll.FindOne(ctx, filter).Decode(&result)

	req := result["req"]

	if err != nil{
		if err == nil {
			return "해당 명령어가 존재하지 않아요!"
		} else {
			fmt.Println(err)
		}
	}
	fmt.Println("데이터 조회에 성공하였습니다.")
	fmt.Printf("조회 커맨드| %s : %s", content, req)
	fmt.Println()
	return req
}

// //SelectAll Later Todo
// func SelectAll (client *mongo.Client, content string, s *discordgo.Session, m *discordgo.MessageCreate) {
// 	coll := client.Database("gogo").Collection("userCommand")
// 	ctx, _ := context.WithTimeout(context.Background(), 5*time.Second)

// 	var results []bson.M

// 	cursor, err := coll.Find(ctx, bson.M{})
// 	if err != nil {
//     log.Fatal(err)
// 	}

// 	if err = cursor.All(ctx, &results); err != nil {
//     log.Fatal(err)
// 	}

// 	//go루틴으로 고치기
// 	for i, result := range results {
// 		fmt.Println(i, result)
// 		res := result["res"]
// 		req := result["req"]

// 		res = res.(string)
// 		req = req.(string)

// 		fmt.Sprint([] int (s)) 

// 		s.ChannelMessageSend(m.ChannelID, result)
// 		fmt.Printf("%d번째 커맨드 %s : %s\n", i+1, res, req)
// 	}
	

// 	fmt.Println(results)
// }