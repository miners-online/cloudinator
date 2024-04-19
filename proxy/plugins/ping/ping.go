package ping

import (
	"context"
	"fmt"
	"os"

	"github.com/go-logr/logr"
	. "github.com/minekube/gate-plugin-template/util"
	"github.com/minekube/gate-plugin-template/util/mini"
	"github.com/robinbraemer/event"
	"go.minekube.com/gate/pkg/edition/java/proxy"
	"gopkg.in/yaml.v3"
)

// Plugin is a ping plugin that handles ping events.
var Plugin = proxy.Plugin{
	Name: "Ping",
	Init: func(ctx context.Context, p *proxy.Proxy) error {
		log := logr.FromContextOrDiscard(ctx)
		log.Info("Hello from Ping plugin!")

		event.Subscribe(p.Event(), 0, onPing())

		return nil
	},
}

type MOTD struct {
	line1 string `yaml:"line1"`
	line2 string `yaml:"line2"`
	line3 string `yaml:"line3"`
}

func onPing() func(*proxy.PingEvent) {
	// read the output.yaml file
	data, err := os.ReadFile("ping.yml")

	if err != nil {
		// panic(err)
	}

	// create a person struct and deserialize the data into that struct
	var motd MOTD

	if err := yaml.Unmarshal(data, &motd); err != nil {
		// panic(err)
		motd.line1 = ""
		motd.line2 = ""
		motd.line3 = ""
	}

	line1 := mini.Parse(
		fmt.Sprintf("%s\n", motd.line1),
	)

	line2 := mini.Parse(
		fmt.Sprintf("%s\n", motd.line2),
	)

	line3 := mini.Parse(
		fmt.Sprintf("%s\n", motd.line3),
	)

	description := Join(line1, line2, line3)

	return func(e *proxy.PingEvent) {
		p := e.Ping()
		p.Description = description
		p.Players.Max = p.Players.Online + 1
	}
}
