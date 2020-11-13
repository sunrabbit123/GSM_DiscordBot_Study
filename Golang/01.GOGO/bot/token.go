package bot

import "flag"

var (
	Token string
)

func init() {
	flag.StringVar(&Token, "t", "", "NzAwOTYzNzM4OTY2MTYzNDg3.XpqlWg.S6xHg_bAWnTtCcBhNm2hHmmJWgs")
	flag.Parse()
}
