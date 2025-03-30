**`/src/main/resources/secret.json` required to run:**
```json
{
  "openai": {
    "url": "https://api.openai.com/v1/assistants",
    "key": "",
    "model": "gpt-4o-mini",
    "max_tokens": 800
  }
}
```
- `openai.url`: OpenAI Assistants API Endpoint
- `openai.key`: OpenAI API key
- `openai.model`: ChatGPT model for runs
- `openai.max_tokens`: Maximum tokens allowed to use per-request
