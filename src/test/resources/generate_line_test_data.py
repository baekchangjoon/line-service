import mysql.connector
from mysql.connector import Error
from faker import Faker
import random
from datetime import datetime, timedelta

# Faker 인스턴스 생성 (한국어 설정)
fake = Faker('ko_KR')

def create_connection():
    try:
        connection = mysql.connector.connect(
            host='127.0.0.1',
            user='root',
            password='1234',
            database='linedb'
        )
        if connection.is_connected():
            print("MySQL 데이터베이스에 연결되었습니다.")
            return connection
    except Error as e:
        print(f"Error: {e}")
        return None

def get_table_schema(connection):
    cursor = connection.cursor()
    cursor.execute("SHOW TABLES")
    tables = cursor.fetchall()
    
    table_schemas = {}
    for table in tables:
        table_name = table[0]
        cursor.execute(f"DESCRIBE {table_name}")
        columns = cursor.fetchall()
        table_schemas[table_name] = columns
    
    cursor.close()
    return table_schemas

def cleanup_data(connection):
    cursor = connection.cursor()
    cursor.execute("SHOW TABLES")
    tables = cursor.fetchall()
    
    for table in tables:
        table_name = table[0]
        if table_name != 'line_plans':  # line_plans 테이블 제외
            try:
                cursor.execute(f"TRUNCATE TABLE {table_name}")
                print(f"{table_name} 테이블의 데이터가 삭제되었습니다.")
            except Error as e:
                print(f"Error truncating {table_name}: {e}")
    
    connection.commit()
    cursor.close()

def generate_line_data(count=100):
    data = []
    for _ in range(count):
        row = {
            'member_id': random.randint(1, 1000),  # 가입 회원 ID
            'line_number': fake.phone_number(),     # 전화번호
            'plan_name': random.choice(['기본요금제', '프리미엄요금제', '스마트요금제', '5G요금제']),  # 요금제
            'status': random.choice(['ACTIVE', 'SUSPENDED', 'CANCELLED'])  # 상태
        }
        data.append(row)
    return data

def generate_line_history_data(count=100):
    data = []
    for _ in range(count):
        row = {
            'line_id': random.randint(1, 100),  # line 테이블의 id 참조
            'action_type': random.choice(['CREATE', 'UPDATE_PLAN', 'CANCEL']),  # 변경 유형
            'action_time': fake.date_time_between(start_date='-1y', end_date='now')  # 변경 시간
        }
        data.append(row)
    return data

def generate_line_plan_data(count=100):
    data = []
    plan_names = ['기본요금제', '프리미엄요금제', '스마트요금제', '5G요금제']
    descriptions = [
        '기본적인 통화와 데이터 제공',
        '고속 데이터와 프리미엄 서비스 제공',
        '스마트폰 최적화 요금제',
        '5G 네트워크 전용 요금제'
    ]
    
    for i in range(count):
        plan_index = i % len(plan_names)
        row = {
            'plan_name': plan_names[plan_index],
            'monthly_fee': random.randint(30000, 100000),  # 월 요금 (3만원 ~ 10만원)
            'description': descriptions[plan_index]
        }
        data.append(row)
    return data

def insert_test_data(connection, table_name, data):
    cursor = connection.cursor()
    
    for row in data:
        columns = ', '.join(row.keys())
        placeholders = ', '.join(['%s'] * len(row))
        query = f"INSERT INTO {table_name} ({columns}) VALUES ({placeholders})"
        
        try:
            cursor.execute(query, list(row.values()))
        except Error as e:
            print(f"Error inserting data into {table_name}: {e}")
            print(f"Query: {query}")
            print(f"Values: {list(row.values())}")
    
    connection.commit()
    cursor.close()

def main():
    connection = create_connection()
    if connection is None:
        return
    
    try:
        # 기존 데이터 삭제
        print("기존 데이터 삭제 중...")
        cleanup_data(connection)
        
        # 각 테이블에 대해 테스트 데이터 생성 및 삽입
        print("\n새로운 테스트 데이터 생성 중...")
        
        # line_plan 테이블 데이터 생성 및 삽입 (먼저 생성)
        print("\nline_plan 테이블에 테스트 데이터 생성 중...")
        line_plan_data = generate_line_plan_data()
        insert_test_data(connection, 'line_plan', line_plan_data)
        print(f"line_plan 테이블에 {len(line_plan_data)}개의 테스트 데이터가 삽입되었습니다.")
        
        # line 테이블 데이터 생성 및 삽입
        print("\nline 테이블에 테스트 데이터 생성 중...")
        line_data = generate_line_data()
        insert_test_data(connection, 'line', line_data)
        print(f"line 테이블에 {len(line_data)}개의 테스트 데이터가 삽입되었습니다.")
        
        # line_history 테이블 데이터 생성 및 삽입
        print("\nline_history 테이블에 테스트 데이터 생성 중...")
        line_history_data = generate_line_history_data()
        insert_test_data(connection, 'line_history', line_history_data)
        print(f"line_history 테이블에 {len(line_history_data)}개의 테스트 데이터가 삽입되었습니다.")
    
    except Error as e:
        print(f"Error: {e}")
    finally:
        if connection.is_connected():
            connection.close()
            print("\nMySQL 연결이 종료되었습니다.")

if __name__ == "__main__":
    main() 