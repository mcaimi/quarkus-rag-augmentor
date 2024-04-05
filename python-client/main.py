#!/usr/bin/env python

import websockets
import asyncio
import argparse
from typing import Callable


async def wsInteract(url: str) -> Callable:
    async with websockets.connect(url) as ws:
        async for msg in ws:
            print(f"AI> {msg}")
            message = input("HUMAN> ")
            if message == "quit":
                break
            await ws.send(message)

# run loop
if __name__ == '__main__':
    parser = argparse.ArgumentParser(prog="RedHat AI CLI", description="TUI Websocket client for RedHat AI")
    parser.add_argument("-u", "--url", action="store", required=True)
    args = parser.parse_args()

    with asyncio.Runner() as runner:
        runner.run(wsInteract(url=args.url))
