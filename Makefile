# Variables
IMAGE_NAME=api-rest-inditex-zara
PORT=8080

# === Build targets ===

build:
	docker build -t $(IMAGE_NAME) --target=build .

build-dev:
	docker build -t $(IMAGE_NAME)-dev --target=dev .

build-prod:
	docker build -t $(IMAGE_NAME)-prod --target=prod .

# === Run containers ===

run-dev:
	docker run --rm -p $(PORT):$(PORT) $(IMAGE_NAME)-dev

run-prod:
	docker run --rm -p $(PORT):$(PORT) $(IMAGE_NAME)-prod

# === Clean images (optional helper) ===

clean:
	docker rmi -f $(IMAGE_NAME) $(IMAGE_NAME)-dev $(IMAGE_NAME)-prod || true
