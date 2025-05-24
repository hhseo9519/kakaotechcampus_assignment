{
"openapi": "3.0.0",
"info": {
"title": "Schedule API",
"version": "1.0.0",
"description": "일정 관리 REST API"
},
"paths": {
"/Schedule": {
"post": {
"summary": "일정 생성",
"requestBody": {
"required": true,
"content": {
"application/json": {
"schema": {
"ref": "#/components/schemas/ScCreateRequestDto"
}
}
}
},
"responses": {
"201": {
"description": "생성 성공",
"content": {
"application/json": {
"schema": {
"ref": "#/components/schemas/ScResponseDto"
}
}
}
}
}
},
"get": {
"summary": "전체 일정 조회",
"parameters": [
{
"name": "author",
"in": "query",
"schema": { "type": "string" },
"required": false
},
{
"name": "updatedDate",
"in": "query",
"schema": { "type": "string", "format": "date" },
"required": false
}
],
"responses": {
"200": {
"description": "조회 성공",
"content": {
"application/json": {
"schema": {
"type": "array",
"items": { "ref": "#/components/schemas/ScResponseDto" }
}
}
}
}
}
}
},
"/Schedule/{id}": {
"get": {
"summary": "단건 일정 조회",
"parameters": [
{
"name": "id",
"in": "path",
"schema": { "type": "integer" },
"required": true
}
],
"responses": {
"200": {
"description": "조회 성공",
"content": {
"application/json": {
"schema": { "ref": "#/components/schemas/ScResponseDto" }
}
}
},
"404": { "description": "찾을 수 없음" }
}
},
"patch": {
"summary": "일정 수정",
"parameters": [
{
"name": "id",
"in": "path",
"schema": { "type": "integer" },
"required": true
}
],
"requestBody": {
"required": true,
"content": {
"application/json": {
"schema": { "ref": "#/components/schemas/ScUpdateRequestDto" }
}
}
},
"responses": {
"200": {
"description": "수정 성공",
"content": {
"application/json": {
"schema": { "ref": "#/components/schemas/ScResponseDto" }
}
}
},
"404": { "description": "ID 없음 또는 비밀번호 불일치" }
}
},
"delete": {
"summary": "일정 삭제",
"parameters": [
{
"name": "id",
"in": "path",
"schema": { "type": "integer" },
"required": true
}
],
"requestBody": {
"required": true,
"content": {
"application/json": {
"schema": { "ref": "#/components/schemas/ScDeleteRequestDto" }
}
}
},
"responses": {
"204": { "description": "삭제 성공" },
"404": { "description": "ID 없음 또는 비밀번호 불일치" }
}
}
}
},
"components": {
"schemas": {
"ScCreateRequestDto": {
"type": "object",
"properties": {
"task": { "type": "string" },
"author": { "type": "string" },
"password": { "type": "string" }
},
"required": ["task", "author", "password"]
},
"ScUpdateRequestDto": {
"type": "object",
"properties": {
"task": { "type": "string" },
"author": { "type": "string" },
"password": { "type": "string" }
},
"required": ["task", "author", "password"]
},
"ScDeleteRequestDto": {
"type": "object",
"properties": {
"password": { "type": "string" }
},
"required": ["password"]
},
"ScResponseDto": {
"type": "object",
"properties": {
"id": { "type": "integer" },
"task": { "type": "string" },
"author": { "type": "string" },
"created_at": { "type": "string", "format": "date-time" },
"updated_at": { "type": "string", "format": "date-time" }
},
"required": ["id", "task", "author", "created_at", "updated_at"]
}
}
}
}


