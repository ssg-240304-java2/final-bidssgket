from PIL import Image
import os

# 이미지 파일의 경로를 저장할 폴더 경로
folder_path = './imageFile'

# 폴더 내 모든 파일에 대해 반복문 실행
for file_name in os.listdir(folder_path):
    # 숨김 파일이 아닌 경우에만 작업 수행
    if not file_name.startswith('._'):
        # 이미지 파일인 경우에만 작업 수행
        if file_name.endswith('.jpg') or file_name.endswith('.jpeg') or file_name.endswith('.png'):
            # 파일 경로 설정
            file_path = os.path.join(folder_path, file_name)
            
            # 썸네일 이미지 생성
            with Image.open(file_path) as img:
                # 썸네일 이미지 크기 지정
                img.thumbnail((1000, 1000)) # (너비, 높이) 튜플 형태로 크기 지정
                # 파일 이름과 경로 생성
                thumb_file_name = "thumb_" +  file_name
                thumb_file_path = os.path.join(folder_path, thumb_file_name)
                # 썸네일 이미지 저장
                img.save(thumb_file_path)
                
                # 생성한 썸네일 이미지 정보 출력
                print(f"{thumb_file_name} : {os.path.getsize(thumb_file_path)} bytes")

