# compress.py
from PIL import Image
import sys

def compress_image(input_path, output_path, quality=70):
    image = Image.open(input_path)
    image.save(output_path, "JPEG", optimize=True, quality=quality)

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python compress.py input.jpg output.jpg")
        sys.exit(1)

    compress_image(sys.argv[1], sys.argv[2])